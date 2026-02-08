# SalesCodingTest_DSG

## How to run the app
Required :

- [ ] Java 21
- [ ] Maven 3.x

Method 1 :
1. Navigate to the project directory : `cd {PATH}/SalesCodingTest_DSG`
2. Create Jar : `mvn clean package`
3. Execute Jar : `java -jar target/SalesCodingTest_DSG-1.0-SNAPSHOT.jar`

Method 2 :
1. Navigate to the project directory : `cd {PATH}/SalesCodingTest_DSG`
2. Execute java with maven : `mvn clean compile exec:java`

## Analysis
The client calls the application to print a receipt. For that, they provide information about each product:
- Quantity
- If it's imported or not
- Name
- Price
  The information are always provided in the same order:  ***quantity (imported) name price.***

### Business rules
- By default, products are subject to 10% sales tax.
- Those product categories are exempt from taxes:
    - books
    - food
    - medical
- Items can be imported, if they are an additional 5% tax will be applied.
- Taxes are rounded up to 0.05.

## Initial Conception
```
DOMAIN 
	Model 
		Category Enum
			String name
			List<String> productNameList
		Product 
			String name
			int quantity 
			Category category
			BigDecimal price
			boolean isImported
		Receipt 
			List<Product> productList
			BigDecmial totalPrice
			BigDecmial totalTax
	Rule
		Tax
			TaxRule (interface)
			BasicSaleTaxRule
			ImportTaxRule
			RoundTaxUpTo05Rule
			
APPLICATION
		Entity
			ProductDto
			BusinessException
			FunctionalException
		Service 
			ReceiptPrinterService
			ProductInputService 
			ExceptionService
		Converter
			InputProductToProductDtoMapper
			ProductDtoToProductMapper
			
INFRASTRUCTURE 
		ConsoleApplication (main)
		Scenario 
			SalesReceiptScenario
		Resources
			InputSales 
```

#### How to map
*Regex: ^(\d+)\s+(imported\s+)?(.+)\s+at\s+(\d+\.\d{2})$*
1. First group = quantity
2. If imported present, isImported = true (removing imported from string)
3. Second group = product name
4. Last group = Product price

## Upgrade to a real scenario

1. Backend should have the responsibility of the product data.
2. A datastore could store the product with: ID, name, category, price, price with taxes.
3. Frontend will call the backend with an item ID and no more with product information.
4. Backend should implement more routes: Create/Update/Delete/Get.
5. Use of spring to inject dependance and remove static from some methods
6. Remplace unitTest by scenario test for an end to end test
7. Adding a logger could be efficient if we need to log more than just errors
- Debug: Helps to find problems
- Warnings: Used when errors are acceptable