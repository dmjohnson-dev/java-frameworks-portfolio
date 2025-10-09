package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
        System.out.println("Started in Bootstrap");
        long productCount = productRepo.count();
        long partCount = partRepo.count();
        System.out.println("Number of Products " + productCount);
        System.out.println("Number of Parts " + partCount);

        // Seed ONLY if both are empty (per requirement)
        if (productCount == 0 && partCount == 0) {
            seed();
        }
    }

    private void seed() {
        // ----- Create Parts (5 total) -----
        InhousePart cpu = new InhousePart();
        cpu.setName("Ryzen 5 CPU");
        cpu.setPrice(199.99);
        cpu.setMin(2); cpu.setMax(50); cpu.setInv(10);
        cpu.setPartId(5601);

        InhousePart ram = new InhousePart();
        ram.setName("16GB DDR4 RAM");
        ram.setPrice(59.99);
        ram.setMin(2); ram.setMax(100); ram.setInv(60);
        ram.setPartId(7702);

        OutsourcedPart ssd = new OutsourcedPart();
        ssd.setName("1TB NVMe SSD");
        ssd.setPrice(89.99);
        ssd.setMin(2); ssd.setMax(80); ssd.setInv(40);
        ssd.setCompanyName("StorageCo");

        OutsourcedPart gpu = new OutsourcedPart();
        gpu.setName("GeForce GTX 1660");
        gpu.setPrice(229.99);
        gpu.setMin(2); gpu.setMax(30); gpu.setInv(12);
        gpu.setCompanyName("Graphix Ltd");

        OutsourcedPart psu = new OutsourcedPart();
        psu.setName("600W Power Supply");
        psu.setPrice(69.99);
        psu.setMin(2); psu.setMax(60); psu.setInv(35);
        psu.setCompanyName("PowerMax");

        partRepo.save(cpu);
        partRepo.save(ram);
        partRepo.save(ssd);
        partRepo.save(gpu);
        partRepo.save(psu);

        // ----- Create Products (5 total) -----
        Product budget = new Product("Budget PC", 599.99, 10);
        Product gaming = new Product("Gaming PC", 1099.99, 5);
        Product creator = new Product("Creator PC", 1299.99, 3);
        Product office = new Product("Office PC", 499.99, 8);
        Product mini = new Product("Mini PC", 399.99, 12);

        // ----- Associate Parts (use Set so duplicates are ignored) -----
        Set<Part> budgetParts = new HashSet<>();
        budgetParts.add(cpu); budgetParts.add(ram); budgetParts.add(ssd);
        budget.setParts(budgetParts);

        Set<Part> gamingParts = new HashSet<>();
        gamingParts.add(cpu); gamingParts.add(ram); gamingParts.add(ssd); gamingParts.add(gpu); gamingParts.add(psu);
        gaming.setParts(gamingParts);

        Set<Part> creatorParts = new HashSet<>();
        creatorParts.add(cpu); creatorParts.add(ram); creatorParts.add(ssd); creatorParts.add(gpu);
        creator.setParts(creatorParts);

        Set<Part> officeParts = new HashSet<>();
        officeParts.add(cpu); officeParts.add(ram); officeParts.add(psu);
        office.setParts(officeParts);

        Set<Part> miniParts = new HashSet<>();
        miniParts.add(ram); miniParts.add(ssd); miniParts.add(psu);
        mini.setParts(miniParts);

        productRepo.save(budget);
        productRepo.save(gaming);
        productRepo.save(creator);
        productRepo.save(office);
        productRepo.save(mini);

        System.out.println("Sample data seeded.");
    }
}
