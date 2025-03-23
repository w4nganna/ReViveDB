INSERT INTO retailers (id, name, email, lat, lng, banner, logo)
VALUES
    ('ret-001', 'Shoppers Drug Mart - Lower Jarvis', 'eco@retailer.com', '43.6534817', '-79.3839347', 'https://www.bloor-yorkville.com/wp-content/uploads/2019/07/SDM-1.jpg','https://mallmaverick.imgix.net/web/property_managers/27/properties/252/all/20220124175645/Shoppers.png'),
    ('ret-002', 'FreshMarket - Queen', 'fresh@market.com', '43.645233', '-79.380989','https://dis-prod.assetful.loblaw.ca/content/dam/loblaw-companies-limited/creative-assets/freshmart/ogimage-freshmart.jpg','https://dis-prod.assetful.loblaw.ca/content/dam/loblaw-companies-limited/creative-assets/freshmart/freshmart-legacy-logo-carousel.svg'),
    ('ret-003', 'No Frills - Spadina', 'green@grocer.com', '43.650204', '-79.386458', 'https://cdn.strategyonline.ca/wp/wp-content/uploads/2017/05/18423163_1330071353767338_6626989049803915215_o.jpg', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVFhleLxFH4rY6O1dQHuMJE-8hpYPa3TUyEA&s');

//INSERT INTO purchases (id, shopperId, productId, quantity, money_saved, purchase_date)
//VALUES
   // (1, 'shop-001', 1001, 2, 0.226, 9.00, '2025-09-01'),
    //(2, 'shop-002', 1003, 1, 0.113, 4.25, '2025-07-14'),
    //(3, 'shop-003', 1006, 3, 0.339, 60.03, '2025-08-12''');

INSERT INTO shoppers (id, name, email)
VALUES
    ('wangan80', 'Anna Wang', 'annacoco.wang@mail.utoronto.cacom'),
    ('shop-002', 'Bob Johnson', 'bob@example.com'),
    ('shop-003', 'Charlie Lee', 'charlie@example.com');

INSERT INTO categories (categoryId, categoryName)
VALUES
    (1, 'Shampoo'),
    (2, 'Conditioner'),
    (3, 'Perfume');

INSERT INTO products( productId, name, brand, originalPrice, newPrice, categoryId, retailerId, imageURL, averageScore)
VALUES
    (1001, 'Frizz- Free Keratine Smoothing Oil Shampoo', 'OGX', 12.99, 8.49, 1, 'ret-001','https://assets.beauty.shoppersdrugmart.ca/bb-prod-product-image/062600385185/enfr/01/front/400/white.jpg', 4),
    (1002, 'Curl Define Shampoo', 'Monday Haircare', 9.99, 6.99, 1, 'ret-002', 'https://digital.loblaws.ca/SDM/SDM_840191601262/en/1/840191601262_enfr_01_400.jpeg', 3.4),
    (1003, 'EverPure Moisture Conditioner', 'L''Oréal Paris', 11.50, 7.25, 2, 'ret-003','https://digital.loblaws.ca/SDM/SDM_071249341254/en/1/0071249341254_enfr_01_400.jpeg', 4.1),
    (1004, 'Dry & Itchy Leave in Conditioner', 'As I Am', 13.00, 9.00, 2, 'ret-001','https://digital.loblaws.ca/SDM/SDM_858380035323/en/1/858380035323_enfr_01_400.jpeg',2.9),
    (1005, 'Eilish Eau de Parfum', 'Billie Eilish', 45.00, 30.00, 3, 'ret-002','https://digital.loblaws.ca/SDM/SDM_608940582206/en/1/608940582206_enfr_01_400.jpeg',3.1),
    (1006, 'Cloud Eau de Parfum Spray', 'Ariana Grande', 60.00, 39.99, 3, 'ret-003','https://digital.loblaws.ca/SDM/SDM_812256023289/en/1/812256023289_enfr_01_400.jpeg', 4.1);