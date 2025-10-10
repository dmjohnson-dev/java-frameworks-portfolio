<strong>** DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. ** </strong>

# WESTERN GOVERNORS UNIVERSITY 
## D287 – JAVA FRAMEWORKS
Welcome to Java Frameworks! This is an opportunity for students to implement user interfaces and learn to leverage existing frameworks, assets, and content for object-oriented programming.
FOR SPECIFIC TASK INSTRUCTIONS AND REQUIREMENTS FOR THIS ASSESSMENT, PLEASE REFER TO THE COURSE PAGE.
## BASIC INSTRUCTIONS
For this project, you will use the Integrated Development Environment (IDE) link in the web links section of this assessment to install the IDE, IntelliJ IDEA (Ultimate Edition). All relevant links are on the course page. Please refer to the course of study for specific links. You will sign up for a free student license using your WGU.edu email address. Please see the “IntelliJ Ultimate Edition Instructions” attachment for instructions on how do this. Next you will download the “Inventory Management Application Template Code” provided in the web links section and open it in IntelliJ IDEA (Ultimate Edition). You will upload this project to a private external GitLab repository and backup regularly. As a part of this, you have been provided with a base code (starting point). 

## SUPPLEMENTAL RESOURCES  
1.	How to clone a project to IntelliJ using Git?

> Ensure that you have Git installed on your system and that IntelliJ is installed using [Toolbox](https://www.jetbrains.com/toolbox-app/). Make sure that you are using version 2022.3.2. Once this has been confirmed, click the clone button and use the 'IntelliJ IDEA (HTTPS)' button. This will open IntelliJ with a prompt to clone the proejct. Save it in a safe location for the directory and press clone. IntelliJ will prompt you for your credentials. Enter in your WGU Credentials and the project will be cloned onto your local machine.  

2. How to create a branch and start Development?

- GitLab method
> Press the '+' button located near your branch name. In the dropdown list, press the 'New branch' button. This will allow you to create a name for your branch. Once the branch has been named, you can select 'Create Branch' to push the branch to your repository.

- IntelliJ method
> In IntelliJ, Go to the 'Git' button on the top toolbar. Select the new branch option and create a name for the branch. Make sure checkout branch is selected and press create. You can now add a commit message and push the new branch to the local repo.

## SUPPORT
If you need additional support, please navigate to the course page and reach out to your course instructor.
## FUTURE USE
Take this opportunity to create or add to a simple resume portfolio to highlight and showcase your work for future use in career search, experience, and education!
## Where to find my changes (Parts C–J)

Part C — Customize HTML UI (shop name, product/part names)
Prompt	File	Line(s)	Change
Add shop name, keep all existing UI	src/main/resources/templates/MainScreen.html	~15–29	Added header block with Custom PC Workshop and nav links. <!-- [C] header -->
Add section headings for Parts/Products	src/main/resources/templates/MainScreen.html	~55, ~97	Added <h2>Parts (Computer Components)</h2> and <h2>Products (Complete PC Builds)</h2> labels.
Add CSS	src/main/resources/static/css/style.css	all	Added simple site styles (header, brand). /* [C] styles */
Wire CSS in page	MainScreen.html (and all forms)	~7–9	<link rel="stylesheet" th:href="@{/css/style.css}">


Part D — Add “About” page + navigation
Prompt	File	Line(s)	Change
Add About page	src/main/resources/templates/about.html	all	New file with brief company description. <!-- [D] about -->
Controller mapping	src/main/java/com/example/demo/controllers/AboutController.java	all	@Controller with @GetMapping("/about") returning about.
Nav links to/from About	MainScreen.html header	~22–28	<a th:href="@{/about}">About</a> and About page link back to @{/mainscreen}.


Part E — Seed sample inventory (5 parts, 5 products), only when DB is empty
Prompt	File	Line(s)	Change
Bootstrap seeding (only when both lists are empty)	src/main/java/com/example/demo/bootstrap/SampleDataLoader.java (or Bootstrap.java)	all	On startup, check productRepository.count()==0 && partRepository.count()==0 then insert sample Parts/Products. // [E] seed
Use Set for product parts	Product.java	~29	Set<Part> parts = new HashSet<>(); ensures no duplicate parts.
Duplicate parts become multi-pack	SampleDataLoader.java	~70–110	When attempting duplicates, add “Multi-pack” instead of duplicate entry (basic example logic).


Part F — “Buy Now” button (decrement product inventory, message on success/failure)
Prompt	File	Line(s)	Change
Add “Buy Now” next to Update/Delete	MainScreen.html (products table)	~122–142	1 button per row: th:href="@{/buyProduct(productID=${tempProduct.id})}". Disables if inv == 0. <!-- [F] buy now -->
Controller endpoint	src/main/java/com/example/demo/controllers/AddProductController.java	~78–115	@GetMapping("/buyProduct") loads product, decrements inv if >0, sets flash message, redirects to /mainscreen.
Flash message display	MainScreen.html	~35–45	Show success/failure with th:if="${message}".

Part G — Track min/max inventory on parts, enforce bounds, update forms, rename storage
Prompt	File	Line(s)	Change
Add min/max fields to Part	src/main/java/com/example/demo/domain/Part.java	~30–44	Added private int min; private int max; with getters/setters and basic annotations. // [G] fields
Include min/max in sample data	SampleDataLoader.java	~55–100	When creating parts, set min and max (default min=2 across app as requested).
Update Inhouse form	templates/InhousePartForm.html	~20–60	Added Min/Max inputs bound to *{min} and *{max}.
Update Outsourced form	templates/OutsourcedPartForm.html	~20–60	Added Min/Max inputs bound to *{min} and *{max}.
Enforce inventory between min and max	AddInhousePartController.java / AddOutsourcedPartController.java	~45–85	Server-side checks: min <= inv <= max and min <= max. Bind errors if invalid.
Rename H2 storage file	src/main/resources/application.properties	~1–5	spring.datasource.url=jdbc:h2:file:~/spring-boot-h2-db102 (renamed from prior).

Part H — Validation messages for min/max violations
Prompt	File	Line(s)	Change
Show error adding/updating parts when inv < min or inv > max	AddInhousePartController.java	~55–80	br.rejectValue("inv", "...", "…") messages; return form with errors.
Show error when adding/updating products would drop a part below min	AddProductController.java	~120–155	On save/update, if product composition reduces part inventory < min (or on purchase), reject with message and keep user on form.
Show error when min > max	AddInhousePartController.java / AddOutsourcedPartController.java	~48–60	br.rejectValue("min", "min.gt.max", "...").

Part I — Unit tests (at least two) for min/max
Prompt	File	Line(s)	Change
min <= inv <= max happy-path	src/test/java/com/example/demo/domain/PartTest.java	~25–55	Creates part with min=2, inv=5, max=10; asserts valid.
Detect inv < min and inv > max	PartTest.java	~57–110	Two tests asserting validator/logic rejects out-of-range values.
Optional product min-protection	src/test/java/com/example/demo/service/ProductServiceTest.java	~20–60	Ensures update/add product won’t drop any associated part below min.

Part J — Remove unused validators
Prompt	File	Line(s)	Change
Deleted unused validator classes	src/main/java/com/example/demo/validators/*	—	Removed any unused validators (kept EnufPartsValidator, ValidProductPrice only if used).
Clean imports	various	—	Removed unused imports and dead code.