package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Product;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    public SampleDataLoader(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        long productCount = productRepository.count();
        long partCount = partRepository.count();
        if (productCount == 0 && partCount == 0) {
            // Parts (min default = 2)
            InhousePart cpu = new InhousePart();
            cpu.setName("Ryzen 5 5600");
            cpu.setPrice(129.99); cpu.setInv(5);
            cpu.setMin(2); cpu.setMax(20); cpu.setPartId(1001);

            InhousePart ram = new InhousePart();
            ram.setName("DDR4 16GB Kit");
            ram.setPrice(39.99); ram.setInv(8);
            ram.setMin(2); ram.setMax(30); ram.setPartId(1002);

            OutsourcedPart ssd = new OutsourcedPart();
            ssd.setName("NVMe 1TB");
            ssd.setPrice(59.99); ssd.setInv(10);
            ssd.setMin(2); ssd.setMax(40); ssd.setCompanyName("FastStorage Inc.");

            OutsourcedPart gpu = new OutsourcedPart();
            gpu.setName("RTX 4060");
            gpu.setPrice(299.99); gpu.setInv(3);
            gpu.setMin(2); gpu.setMax(10); gpu.setCompanyName("GraphiMax");

            OutsourcedPart psu = new OutsourcedPart();
            psu.setName("650W PSU");
            psu.setPrice(59.99); psu.setInv(6);
            psu.setMin(2); psu.setMax(25); psu.setCompanyName("PowerPro");

            partRepository.save(cpu);
            partRepository.save(ram);
            partRepository.save(ssd);
            partRepository.save(gpu);
            partRepository.save(psu);

            // Products
            Product budget = new Product("Budget Build", 599.99, 4);
            Product gaming = new Product("Gaming Build", 999.99, 3);
            Product creator = new Product("Creator Build", 1299.99, 2);
            Product office = new Product("Office Build", 499.99, 5);
            Product sff    = new Product("SFF Build", 799.99, 2);

            productRepository.save(budget);
            productRepository.save(gaming);
            productRepository.save(creator);
            productRepository.save(office);
            productRepository.save(sff);
        }
    }
}
