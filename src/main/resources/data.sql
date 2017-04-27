insert into role(id, role) VALUES (null,"ROLE_USER"), (null, "ROLE_ADMIN");

INSERT INTO `user` (`id`,`user_email`,`first_name`,`password`,`date_registration`)
VALUES
  (1,'michlu@o2.pl','Nowin','$2a$10$nWPT.sl.oljfWR5zu.BSwuOw67CsDM2XqjQ8P0rhjpXUr1MgEWf62','2017-04-10'),
  (2,'asd@asd.pl','User','$2a$10$rwDRCT2OdhQP/8xHD3IXmOhfiaYe7XduwR9g9y1HVulFDB0lZx69O','2017-04-14');

INSERT INTO `user_role` (`user_id`,`role_id`) VALUES
  (1,1),
  (2,1),
  (1,2);

INSERT INTO `category` (`id`,`description`,`category_name`,`urlImage`)
VALUES
  (1,'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Laboratorium','/resources/images/category_laboratory.png'),
  (2,'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Geografia','/resources/images/category_geography.png'),
  (3,'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Biologia','/resources/images/category_biology.png'),
  (4,'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Java','/resources/images/category_java.png'),
  (5,'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Historia','/resources/images/category_history.png'),
  (6,'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.','Sport i dieta','/resources/images/category_sport.png');

INSERT INTO `question`
VALUES
  (1,'Czego dotyczy powiedzenie: „Każdy Polak kocha C2H5OH”?',1),
  (2,'Który z wymienionych związków jest kwasem?',1),
  (3,'Do czego stosuje się kwas azotowy i siarkowy na skalę przemysłową?',1),
  (4,'Jaka jest systematyczna nazwa kwasu acetylosalicylowego?',1),
  (5,'Eksfoliacja jest procesem:',1),(6,'Destylacja prosta polega na:',1),
  (7,'Który z wymienionych krajów nie posiada własnego pierwiastka chemicznego?',1),
  (8,'Który z pierwiastków chemicznych nie jest ciałem stałym?',1),
  (9,'Który z podanych związków jest niemetalem?',1),
  (10,'Do jakiej grupy związków organicznych należą między innymi występujące w przyrodzie: hem, chlorofil oraz turacyna?',1),
  (11,'Symbolem chemicznym którego pierwiastka jest Ce?',1),
  (12,'Który metal najrzadziej występuje w skorupie ziemskiej?',1),
  (13,'Ile patentów uzyskała Maria Skłodowska-Curie?',1),
  (14,'Dlaczego wiele starych zdjęć wywoływanych było w kolorze sepii?',1),
  (15,'Jakie działanie na ludzki organizm ma kwas nikotynowy?',1),
  (16,'Jaka właściwość zubożonego uranu powoduje, że używa się go do produkcji amunicji?',1),
  (17,'Jakie cechy miałby pocisk wykonany ze złota?',1),
  (18,'Co jest substancją czynną gazu pieprzowego, używanego do samoobrony?',1),
  (19,'Do czego stosuje się czysty tlen?',1),
  (20,'CaCO3 to wzór jakiego związku?',1);

INSERT INTO `answer`
VALUES
  (1,'Etanolu',1,1),(2,'Etylenu',0,1),(3,'Eteru',0,1),(4,'Eugenolu',0,1),(5,'H2O',0,2),
  (6,'CH3OH',0,2),(7,'CH4',0,2),(8,'CH3COOH',1,2),(9,'Produkcji materiałów wybuchowych',0,3),
  (10,'Rafinacji ropy naftowej',0,3),(11,'Produkcji nawozów sztucznych',1,3),(12,'Procesu eksfoliacji',0,3),
  (13,'Aspiryna',1,4),(14,'Witamina C',0,4),(15,'Chinina ',0,4),(16,'Saletra chilijska',0,4),
  (17,'Rozszczepiania cząsteczek polipropylenu',0,5),(18,'Złuszczania naskórka',1,5),
  (19,'Wypalania cebulek włosa',0,5),(20,'Usieciowania włókien poliwęglanowych',0,5),
  (21,'Rozdzieleniu mieszaniny cieczy wskutek odparowania',1,6),
  (22,'Wyodrębnianiu składnika mieszaniny poprzez dyfuzję',0,6),
  (23,'Wyodrębnieniu rozpuszczonego ciała stałego z roztworu',0,6),
  (24,'Powstawaniu dynamicznej zawiesiny cieczy w strumieniu gazu',0,6),
  (25,'Polska',0,7),(26,'Włochy',1,7),(27,'Francja',0,7),(28,'Niemcy',0,7),
  (29,'Na',0,8),(30,'K',0,8),(31,'Be',0,8),(32,'H',1,8),(33,'Beryl',0,9),
  (34,'Fluor',1,9),(35,'Sód',0,9),(36,'Wapń',0,9),(37,'Estrów',0,10),
  (38,'Pofiryn',1,10),(39,'Alkoholi',0,10),(40,'Dienów sprzężonych',0,10),
  (41,'Cez',0,11),(42,'Cer',1,11),(43,'Cyna',0,11),(44,'Cyrkon',0,11),
  (45,'Złoto',0,12),(46,'Platyna',0,12),(47,'Cyrkon',0,12),(48,'Ren',1,12),
  (49,'Jeden',0,13),(50,'Dwa – na polon i rad',0,13),(51,'Żadnego',1,13),
  (52,'Pięć',0,13),(53,'Wywoływanie w sepii było tańsze od zdjęć czarno-białych',0,14),
  (54,'Barwienie sepią zwiększało trwałość zdjęcia',1,14),(55,'Aby ukryć skutki starzenia się papieru',0,14),
  (56,'Sepia jest naturalnym skutkiem działania słońca',0,14),(57,'Jest substancją neutralną dla organizmu',0,15),
  (58,'Jest szkodliwy, przyczynia się do powstawania nowotworów',0,15),
  (59,'Jest silnie toksyczny, w dużych stężeniach jest śmiertelny',0,15),(60,'Jest niezbędny dla organizmu',1,15),
  (61,'Radioaktywność',0,16),(62,'Wysoka gęstość',1,16),(63,'Wybuchowość',0,16),(64,'Wyjątkowa twardość',0,16),
  (65,'Byłby bezużyteczny',0,17),(66,'Byłby przeciwpancerny',1,17),(67,'Byłby wybuchowy',0,17),
  (68,'Miałby takie same cechy jak pocisk ołowiany',0,17),(69,'Amoniak',0,18),(70,'Kwas mrówkowy',0,18),
  (71,'Piperyna, znana z pieprzu',0,18),(72,'Kapsaicyna, znana z papryki',1,18),(73,'Do spawania',1,19),
  (74,'Do czyszczenia',0,19),(75,'Do malowania',0,19),(76,'Do gaszenia ognia',0,19),
  (77,'Kwasu węglowego',0,20),(78,'Węglanu sodu',0,20),(79,'Węglanu wapnia',1,20),(80,'Nadtlenku wapnia',0,20);
