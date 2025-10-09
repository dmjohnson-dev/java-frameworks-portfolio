package com.example.demo.bootstrap;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final PartRepository partRepo;
    private final ProductRepository productRepo;

    public SampleDataLoader(PartRepository partRepo, ProductRepository productRepo) {
        this.partRepo = partRepo;
        this.productRepo = productRepo;
    }

    @Override
    public void run(String... args) {
        System.out.println("Started in SampleDataLoader");
        long partCount = partRepo.count();
        long productCount = productRepo.count();
        System.out.println("Existing parts: " + partCount + ", products: " + productCount);

        // Only seed when BOTH are empty (per requirement)
        if (partCount == 0 && productCount == 0) {
            seed();
        } else {
            System.out.println("Skipping sample data seeding (database not empty).");
        }
    }

    private void seed() {
        // ----- 5 Parts -----
        InhousePart cpu = new InhousePart();
        cpu.setName("Ryzen 5 7600");
        cpu.setPrice(199.99);
        cpu.setInv(25);
        cpu.setPartId(7600);

        InhousePart ram = new InhousePart();
        ram.setName("16GB DDR5 Kit");
        ram.setPrice(59.99);
        ram.setInv(50);
        ram.setPartId(16005);

        OutsourcedPart ssd = new OutsourcedPart();
        ssd.setName("1TB NVMe SSD");
        ssd.setPrice(79.99);
        ssd.setInv(40);
        ssd.setCompanyName("FlashCore");

        OutsourcedPart gpu = new OutsourcedPart();
        gpu.setName("GeForce RTX 4060");
        gpu.setPrice(299.99);
        gpu.setInv(15);
        gpu.setCompanyName("Nvidia Partner");

        InhousePart psu = new InhousePart();
        psu.setName("650W 80+ Gold PSU");
        psu.setPrice(89.99);
        psu.setInv(30);
        psu.setPartId(65080);

        partRepo.save(cpu);
        partRepo.save(ram);
        partRepo.save(ssd);
        partRepo.save(gpu);
        partRepo.save(psu);

        // ----- 5 Products (Set prevents duplicate parts) -----
        Product budgetPC  = new Product("Budget PC",  649.99, 10);
        addPartsWithMultipack(budgetPC, cpu, ram, ssd, psu);

        Product gamingPC  = new Product("Gaming PC", 1299.99, 6);
        addPartsWithMultipack(gamingPC, cpu, ram, ssd, gpu, psu);

        Product creatorPC = new Product("Creator PC", 1499.99, 4);
        // duplicate ram on purpose → multipack
        addPartsWithMultipack(creatorPC, cpu, ram, ssd, gpu, psu, ram);

        Product officePC  = new Product("Office PC",  699.99, 8);
        addPartsWithMultipack(officePC, cpu, ram, ssd, psu);

        Product miniPC    = new Product("Mini PC",    599.99, 5);
        addPartsWithMultipack(miniPC, cpu, ram, ssd, psu);

        productRepo.save(budgetPC);
        productRepo.save(gamingPC);
        productRepo.save(creatorPC);
        productRepo.save(officePC);
        productRepo.save(miniPC);

        System.out.println("Sample inventory seeded: 5 parts, 5 products.");
    }

    /** Adds parts; if a duplicate is detected, create a 2-pack variant of the same subtype. */
    private void addPartsWithMultipack(Product product, Part... parts) {
        for (Part p : parts) {
            boolean added = product.addPart(p); // Product.addPart uses Set.add under the hood
            if (!added) {
                Part multi = makeMultiPack(p, 2);
                multi = partRepo.save(multi); // persist so it has its own id
                product.addPart(multi);
            }
        }
    }

    /** Never instantiate abstract Part. Always clone as the same concrete subtype. */
    private Part makeMultiPack(Part original, int k) {
        String newName = original.getName() + " (" + k + "-Pack)";
        double newPrice = Math.round(original.getPrice() * k * 100.0) / 100.0;

        if (original instanceof InhousePart) {
            InhousePart src = (InhousePart) original;
            InhousePart mp = new InhousePart();
            mp.setName(newName);
            mp.setPrice(newPrice);
            mp.setInv(src.getInv());
            mp.setPartId(src.getPartId()); // reuse; adjust if you enforce uniqueness
            return mp;
        } else if (original instanceof OutsourcedPart) {
            OutsourcedPart src = (OutsourcedPart) original;
            OutsourcedPart mp = new OutsourcedPart();
            mp.setName(newName);
            mp.setPrice(newPrice);
            mp.setInv(src.getInv());
            mp.setCompanyName(src.getCompanyName());
            return mp;
        } else {
            throw new IllegalArgumentException("Unsupported Part subtype for multipack: " + original.getClass().getName());
        }
    }
}


