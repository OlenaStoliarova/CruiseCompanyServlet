### Система Круизная Компания. 
У <b>Компании</b> имеется несколько Кораблей</b>. У <b>Кораблей</b> есть пассажиро-вместимость, маршрут, количество посещаемых портов, длительность одного круиза. <b>Клиент</b> выбирает и оплачивает круиз. Выбирает <b>Экскурсии</b> по прибытии в порт за дополнительную плату. <b>Администратор Корабля</b> указывает бонусы для пассажиров, учитывая класс билета (бассейн, спорт зал, кинозал,
косметические салоны...).


#### Using:
1. DB - MySql
2. Java version 8.
3. Maven

#### How to install:

1. Clone project
2. Run DBStructureAndData.sql from resources/DB folder
3. Update DB login and password in src\main\webapp\META-INF\context.xml
4. Compile and run war
   - either Add Configuration with type Maven, command line - tomcat7:run, and Before launch Run Maven Goal - clean
   - or Run in terminal command 'mvn clean tomcat7:run'
5. Go to link http://localhost:8888/
6. Just in case here are credentials of already existing users:
  - admin@a.a (password:admin, ROLE_ADMIN)
  - boss@c.ua (password:boss, ROLE_TRAVEL_AGENT)
  - misha@b.ua (password:user, ROLE_TOURIST)
