INSERT INTO "user_pos" VALUES
(1765300843053,1765300843053,X'e880a105a9f94c9fbe887701498128bb','admin@gdgguadalajara.com','admin'),
(1765300843053,1765300843053,X'4a42bacb9a4a423b82414d6685e2dd3f','cashier@gdgguadalajara.com','cashier'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, X'c4a1d2e3f4b54c6d8e7f9a0b1c2d3e4f', 'waiter@gdgguadalajara.com', 'waiter');

INSERT INTO "Account" VALUES
(1765300843055,1765300843055,X'dba1347571a242e19c276068ad376394',X'e880a105a9f94c9fbe887701498128bb','fb001dfcffd1c899f3297871406242f097aecf1a5342ccf3ebcd116146188e4b','ADMIN','ACTIVE','admin'),
(1765300843055,1765300843055,X'a160c95568864a42a98b328bc24ea995',X'4a42bacb9a4a423b82414d6685e2dd3f','5c059532f570b150b396b6da22f56a6595efc66775bbf2dde2217d6b86bcfa6b','CASHIER','ACTIVE','cashier'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, X'd5b2e3f4a5c64d7e9f0a1b2c3d4e5f6a', X'c4a1d2e3f4b54c6d8e7f9a0b1c2d3e4f', 'c76510aa6e1941eaee9f450d1178d7300bd7fdf7c62736c9229a3fc5d10503c9', 'WAITER', 'ACTIVE', 'waiter');

INSERT INTO "Category" (availableFrom, availableFromTime, availableUntil, availableUntilTime, createdAt, updatedAt, isEnabled, id, description, name) VALUES
(CAST(strftime('%s', 'now') AS INTEGER) * 1000,54000000,CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000,64800000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,true,X'8dc5adc6a42b4b3fb450256c9a3415a6','comidas de 9 a 13 hrs','Desayunos'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000,68400000,CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000,100800000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,true,X'0048970f4bac4f7b8aef5d483394305e','comidas de 13 a 22 hrs','Comidas'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000,54000000,CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000,100800000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,true,X'8896a2209e75438c98eb6e3ceebaace1','Bebidas de 9 a 22 hrs','Bebidas'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000,68400000,CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000,104400000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,true,X'ac75d171708444dd871481f7cb8863b1','Bebidas de 1 a 23 hrs','Alcohol'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000,64800000,CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000,100800000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,CAST(strftime('%s', 'now') AS INTEGER) * 1000,true,X'1518d8b919ef448abe6a79e63a05896d','Promociones de de 12 a 22 hrs','Promociones');

INSERT INTO "category_available_days" VALUES
(X'8dc5adc6a42b4b3fb450256c9a3415a6','MONDAY'),
(X'8dc5adc6a42b4b3fb450256c9a3415a6','TUESDAY'),
(X'8dc5adc6a42b4b3fb450256c9a3415a6','WEDNESDAY'),
(X'8dc5adc6a42b4b3fb450256c9a3415a6','THURSDAY'),
(X'8dc5adc6a42b4b3fb450256c9a3415a6','FRIDAY'),
(X'8dc5adc6a42b4b3fb450256c9a3415a6','SATURDAY'),
(X'8dc5adc6a42b4b3fb450256c9a3415a6','SUNDAY'),
(X'0048970f4bac4f7b8aef5d483394305e','MONDAY'),
(X'0048970f4bac4f7b8aef5d483394305e','TUESDAY'),
(X'0048970f4bac4f7b8aef5d483394305e','WEDNESDAY'),
(X'0048970f4bac4f7b8aef5d483394305e','THURSDAY'),
(X'0048970f4bac4f7b8aef5d483394305e','FRIDAY'),
(X'0048970f4bac4f7b8aef5d483394305e','SATURDAY'),
(X'0048970f4bac4f7b8aef5d483394305e','SUNDAY'),
(X'8896a2209e75438c98eb6e3ceebaace1','MONDAY'),
(X'8896a2209e75438c98eb6e3ceebaace1','TUESDAY'),
(X'8896a2209e75438c98eb6e3ceebaace1','WEDNESDAY'),
(X'8896a2209e75438c98eb6e3ceebaace1','THURSDAY'),
(X'8896a2209e75438c98eb6e3ceebaace1','FRIDAY'),
(X'8896a2209e75438c98eb6e3ceebaace1','SATURDAY'),
(X'8896a2209e75438c98eb6e3ceebaace1','SUNDAY'),
(X'ac75d171708444dd871481f7cb8863b1','MONDAY'),
(X'ac75d171708444dd871481f7cb8863b1','TUESDAY'),
(X'ac75d171708444dd871481f7cb8863b1','WEDNESDAY'),
(X'ac75d171708444dd871481f7cb8863b1','THURSDAY'),
(X'ac75d171708444dd871481f7cb8863b1','FRIDAY'),
(X'ac75d171708444dd871481f7cb8863b1','SATURDAY'),
(X'ac75d171708444dd871481f7cb8863b1','SUNDAY'),
(X'1518d8b919ef448abe6a79e63a05896d','MONDAY'),
(X'1518d8b919ef448abe6a79e63a05896d','TUESDAY'),
(X'1518d8b919ef448abe6a79e63a05896d','WEDNESDAY'),
(X'1518d8b919ef448abe6a79e63a05896d','THURSDAY'),
(X'1518d8b919ef448abe6a79e63a05896d','FRIDAY'),
(X'1518d8b919ef448abe6a79e63a05896d','SATURDAY'),
(X'1518d8b919ef448abe6a79e63a05896d','SUNDAY');

INSERT INTO "Product" (availableFrom, availableFromTime, availableUntil, availableUntilTime, price, createdAt, updatedAt, isEnabled, id, description, name) VALUES
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'91a7ea0093ad4492a3135a1cf584a19c', 'Totopos con salsa', 'Chilaquiles'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 15.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'08f3f6ce09a54da1a19c8aebae4910c4', 'Huevos preparados al gusto', 'Huevos'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 60.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'e15f6e8ac0d24afcae96c5de62a60ae6', 'Pan brioche empapado en una mezcla de huevo y canela', 'Tostada Francesa Clásica'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 40.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'233bf3021ba3409591a4f2279895cedb', 'Batido de frutas con granola y semillas', 'Smoothie Bowl'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'4e0634822fe14810bc7dfc4e8682baaf', 'Tortillas de maíz rellenas de pescado', 'Tacos de Pescado'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 90.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'875f3765bbd14fcb80fe85acc73069c5', 'Lechuga romana, crutones y pollo', 'Ensalada César'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 80.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'9905fa7e21a0425c87b295c770718e3e', 'Pasta fresca con salsa de pesto', 'Pasta al Pesto'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 95.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'15e4227a6f254e17a095d3c9d523ecd3', 'Res con lechuga, tomate y cebolla', 'Burger Clásica'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'7f61897feff04768930b975a4c2bb743', 'Limonada fresca con menta', 'Limonada Natural'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'f1dc3e7ce2bb478288f479011773ca78', 'Mezcla de plátano, fresas y yogur', 'Batido de Frutas'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'bd31502740df4480baf81952c227e2c9', 'Té negro frío con limón y menta', 'Té Helado'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'02b473cd8d55493bb0d50dd77996da13', 'Refresco natural de flor de jamaica', 'Agua de Jamaica'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'715b14a8aa954cd9909328469a6db939', 'Ron, menta fresca, azúcar y soda', 'Mojito Clásico'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'9e1cf5081c0041a993ae8b7c2dc53d00', 'Cerveza elaborada localmente', 'Cerveza Artesanal'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'4958cb91af81409796746e34fcafb82a', 'Tequila, jugo de limón y naranja', 'Cóctel Margarita'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'ef314f829b28457e88ddd55fecd841a1', 'Vino tinto suave y afrutado', 'Vino Tinto de la Casa'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'ef5f99932ddc457baedcadfb5d2d3de2', 'Desayuno + café o té', 'Combo Desayuno'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 61200000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 70560000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'08bcf90b4a194c0e802d97c3b342244b', '2x1 en cervezas y cócteles', 'Happy Hour'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'cc18143320c74e339e299a840bf01f64', 'Plato del día + bebida', 'Menú del Día'),
(CAST(strftime('%s', 'now') AS INTEGER) * 1000, 21600000, CAST(strftime('%s', DATE('now', '+1 year')) AS INTEGER) * 1000, 107999000, 50.0, CAST(strftime('%s', 'now') AS INTEGER) * 1000, CAST(strftime('%s', 'now') AS INTEGER) * 1000, true, X'6641bbfebdf942be8611136c7e33f10d', 'Descuento grupos 4+', 'Día Familiar');

INSERT INTO "product_available_days" VALUES
(X'91a7ea0093ad4492a3135a1cf584a19c','MONDAY'),(X'91a7ea0093ad4492a3135a1cf584a19c','TUESDAY'),(X'91a7ea0093ad4492a3135a1cf584a19c','WEDNESDAY'),(X'91a7ea0093ad4492a3135a1cf584a19c','THURSDAY'),(X'91a7ea0093ad4492a3135a1cf584a19c','FRIDAY'),(X'91a7ea0093ad4492a3135a1cf584a19c','SATURDAY'),(X'91a7ea0093ad4492a3135a1cf584a19c','SUNDAY'),
(X'08f3f6ce09a54da1a19c8aebae4910c4','MONDAY'),(X'08f3f6ce09a54da1a19c8aebae4910c4','TUESDAY'),(X'08f3f6ce09a54da1a19c8aebae4910c4','WEDNESDAY'),(X'08f3f6ce09a54da1a19c8aebae4910c4','THURSDAY'),(X'08f3f6ce09a54da1a19c8aebae4910c4','FRIDAY'),(X'08f3f6ce09a54da1a19c8aebae4910c4','SATURDAY'),(X'08f3f6ce09a54da1a19c8aebae4910c4','SUNDAY'),
(X'e15f6e8ac0d24afcae96c5de62a60ae6','MONDAY'),(X'e15f6e8ac0d24afcae96c5de62a60ae6','TUESDAY'),(X'e15f6e8ac0d24afcae96c5de62a60ae6','WEDNESDAY'),(X'e15f6e8ac0d24afcae96c5de62a60ae6','THURSDAY'),(X'e15f6e8ac0d24afcae96c5de62a60ae6','FRIDAY'),(X'e15f6e8ac0d24afcae96c5de62a60ae6','SATURDAY'),(X'e15f6e8ac0d24afcae96c5de62a60ae6','SUNDAY'),
(X'233bf3021ba3409591a4f2279895cedb','MONDAY'),(X'233bf3021ba3409591a4f2279895cedb','TUESDAY'),(X'233bf3021ba3409591a4f2279895cedb','WEDNESDAY'),(X'233bf3021ba3409591a4f2279895cedb','THURSDAY'),(X'233bf3021ba3409591a4f2279895cedb','FRIDAY'),(X'233bf3021ba3409591a4f2279895cedb','SATURDAY'),(X'233bf3021ba3409591a4f2279895cedb','SUNDAY'),
(X'4e0634822fe14810bc7dfc4e8682baaf','MONDAY'),(X'4e0634822fe14810bc7dfc4e8682baaf','TUESDAY'),(X'4e0634822fe14810bc7dfc4e8682baaf','WEDNESDAY'),(X'4e0634822fe14810bc7dfc4e8682baaf','THURSDAY'),(X'4e0634822fe14810bc7dfc4e8682baaf','FRIDAY'),(X'4e0634822fe14810bc7dfc4e8682baaf','SATURDAY'),(X'4e0634822fe14810bc7dfc4e8682baaf','SUNDAY'),
(X'875f3765bbd14fcb80fe85acc73069c5','MONDAY'),(X'875f3765bbd14fcb80fe85acc73069c5','TUESDAY'),(X'875f3765bbd14fcb80fe85acc73069c5','WEDNESDAY'),(X'875f3765bbd14fcb80fe85acc73069c5','THURSDAY'),(X'875f3765bbd14fcb80fe85acc73069c5','FRIDAY'),(X'875f3765bbd14fcb80fe85acc73069c5','SATURDAY'),(X'875f3765bbd14fcb80fe85acc73069c5','SUNDAY'),
(X'9905fa7e21a0425c87b295c770718e3e','MONDAY'),(X'9905fa7e21a0425c87b295c770718e3e','TUESDAY'),(X'9905fa7e21a0425c87b295c770718e3e','WEDNESDAY'),(X'9905fa7e21a0425c87b295c770718e3e','THURSDAY'),(X'9905fa7e21a0425c87b295c770718e3e','FRIDAY'),(X'9905fa7e21a0425c87b295c770718e3e','SATURDAY'),(X'9905fa7e21a0425c87b295c770718e3e','SUNDAY'),
(X'15e4227a6f254e17a095d3c9d523ecd3','MONDAY'),(X'15e4227a6f254e17a095d3c9d523ecd3','TUESDAY'),(X'15e4227a6f254e17a095d3c9d523ecd3','WEDNESDAY'),(X'15e4227a6f254e17a095d3c9d523ecd3','THURSDAY'),(X'15e4227a6f254e17a095d3c9d523ecd3','FRIDAY'),(X'15e4227a6f254e17a095d3c9d523ecd3','SATURDAY'),(X'15e4227a6f254e17a095d3c9d523ecd3','SUNDAY'),
(X'7f61897feff04768930b975a4c2bb743','MONDAY'),(X'7f61897feff04768930b975a4c2bb743','TUESDAY'),(X'7f61897feff04768930b975a4c2bb743','WEDNESDAY'),(X'7f61897feff04768930b975a4c2bb743','THURSDAY'),(X'7f61897feff04768930b975a4c2bb743','FRIDAY'),(X'7f61897feff04768930b975a4c2bb743','SATURDAY'),(X'7f61897feff04768930b975a4c2bb743','SUNDAY'),
(X'f1dc3e7ce2bb478288f479011773ca78','MONDAY'),(X'f1dc3e7ce2bb478288f479011773ca78','TUESDAY'),(X'f1dc3e7ce2bb478288f479011773ca78','WEDNESDAY'),(X'f1dc3e7ce2bb478288f479011773ca78','THURSDAY'),(X'f1dc3e7ce2bb478288f479011773ca78','FRIDAY'),(X'f1dc3e7ce2bb478288f479011773ca78','SATURDAY'),(X'f1dc3e7ce2bb478288f479011773ca78','SUNDAY'),
(X'bd31502740df4480baf81952c227e2c9','MONDAY'),(X'bd31502740df4480baf81952c227e2c9','TUESDAY'),(X'bd31502740df4480baf81952c227e2c9','WEDNESDAY'),(X'bd31502740df4480baf81952c227e2c9','THURSDAY'),(X'bd31502740df4480baf81952c227e2c9','FRIDAY'),(X'bd31502740df4480baf81952c227e2c9','SATURDAY'),(X'bd31502740df4480baf81952c227e2c9','SUNDAY'),
(X'02b473cd8d55493bb0d50dd77996da13','MONDAY'),(X'02b473cd8d55493bb0d50dd77996da13','TUESDAY'),(X'02b473cd8d55493bb0d50dd77996da13','WEDNESDAY'),(X'02b473cd8d55493bb0d50dd77996da13','THURSDAY'),(X'02b473cd8d55493bb0d50dd77996da13','FRIDAY'),(X'02b473cd8d55493bb0d50dd77996da13','SATURDAY'),(X'02b473cd8d55493bb0d50dd77996da13','SUNDAY'),
(X'715b14a8aa954cd9909328469a6db939','MONDAY'),(X'715b14a8aa954cd9909328469a6db939','TUESDAY'),(X'715b14a8aa954cd9909328469a6db939','WEDNESDAY'),(X'715b14a8aa954cd9909328469a6db939','THURSDAY'),(X'715b14a8aa954cd9909328469a6db939','FRIDAY'),(X'715b14a8aa954cd9909328469a6db939','SATURDAY'),(X'715b14a8aa954cd9909328469a6db939','SUNDAY'),
(X'9e1cf5081c0041a993ae8b7c2dc53d00','MONDAY'),(X'9e1cf5081c0041a993ae8b7c2dc53d00','TUESDAY'),(X'9e1cf5081c0041a993ae8b7c2dc53d00','WEDNESDAY'),(X'9e1cf5081c0041a993ae8b7c2dc53d00','THURSDAY'),(X'9e1cf5081c0041a993ae8b7c2dc53d00','FRIDAY'),(X'9e1cf5081c0041a993ae8b7c2dc53d00','SATURDAY'),(X'9e1cf5081c0041a993ae8b7c2dc53d00','SUNDAY'),
(X'4958cb91af81409796746e34fcafb82a','MONDAY'),(X'4958cb91af81409796746e34fcafb82a','TUESDAY'),(X'4958cb91af81409796746e34fcafb82a','WEDNESDAY'),(X'4958cb91af81409796746e34fcafb82a','THURSDAY'),(X'4958cb91af81409796746e34fcafb82a','FRIDAY'),(X'4958cb91af81409796746e34fcafb82a','SATURDAY'),(X'4958cb91af81409796746e34fcafb82a','SUNDAY'),
(X'ef314f829b28457e88ddd55fecd841a1','MONDAY'),(X'ef314f829b28457e88ddd55fecd841a1','TUESDAY'),(X'ef314f829b28457e88ddd55fecd841a1','WEDNESDAY'),(X'ef314f829b28457e88ddd55fecd841a1','THURSDAY'),(X'ef314f829b28457e88ddd55fecd841a1','FRIDAY'),(X'ef314f829b28457e88ddd55fecd841a1','SATURDAY'),(X'ef314f829b28457e88ddd55fecd841a1','SUNDAY'),
(X'ef5f99932ddc457baedcadfb5d2d3de2','MONDAY'),(X'ef5f99932ddc457baedcadfb5d2d3de2','TUESDAY'),(X'ef5f99932ddc457baedcadfb5d2d3de2','WEDNESDAY'),(X'ef5f99932ddc457baedcadfb5d2d3de2','THURSDAY'),(X'ef5f99932ddc457baedcadfb5d2d3de2','FRIDAY'),(X'ef5f99932ddc457baedcadfb5d2d3de2','SATURDAY'),(X'ef5f99932ddc457baedcadfb5d2d3de2','SUNDAY'),
(X'08bcf90b4a194c0e802d97c3b342244b','MONDAY'),(X'08bcf90b4a194c0e802d97c3b342244b','TUESDAY'),(X'08bcf90b4a194c0e802d97c3b342244b','WEDNESDAY'),(X'08bcf90b4a194c0e802d97c3b342244b','THURSDAY'),(X'08bcf90b4a194c0e802d97c3b342244b','FRIDAY'),(X'08bcf90b4a194c0e802d97c3b342244b','SATURDAY'),(X'08bcf90b4a194c0e802d97c3b342244b','SUNDAY'),
(X'cc18143320c74e339e299a840bf01f64','MONDAY'),(X'cc18143320c74e339e299a840bf01f64','TUESDAY'),(X'cc18143320c74e339e299a840bf01f64','WEDNESDAY'),(X'cc18143320c74e339e299a840bf01f64','THURSDAY'),(X'cc18143320c74e339e299a840bf01f64','FRIDAY'),(X'cc18143320c74e339e299a840bf01f64','SATURDAY'),(X'cc18143320c74e339e299a840bf01f64','SUNDAY'),
(X'6641bbfebdf942be8611136c7e33f10d','MONDAY'),(X'6641bbfebdf942be8611136c7e33f10d','TUESDAY'),(X'6641bbfebdf942be8611136c7e33f10d','WEDNESDAY'),(X'6641bbfebdf942be8611136c7e33f10d','THURSDAY'),(X'6641bbfebdf942be8611136c7e33f10d','FRIDAY'),(X'6641bbfebdf942be8611136c7e33f10d','SATURDAY'),(X'6641bbfebdf942be8611136c7e33f10d','SUNDAY');



