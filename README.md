# Health-Management-System
## SpringBoot+SpringSecurity+微信小程序+vue2.0 ##
***
### 关键技术
&emsp;前端使用了vue2.0进行开发，后端使用了mybatis框架，使用了Spring Security和JWT两种技术，将两者结合起来，用于实现用户认证和授权，设计登录验证等功能。

&emsp;本系统使用的是前后端分离开发，前后端服务器使用的端口号不一样，如果不做设置，前端和后端的交互会有错误。所以在后端开发时，使用注解开发的方式来配置WebMvcConfigurer，允许前端资源访问后端端口。

&emsp;因为本系统使用了SpringSecurity和JWT的技术来进行用户的权限验证，每次访问不同的前端页面，向后端发送请求时，都需要对数据库进行查询来验证用户的权限等信息，这样的行为会增加系统与数据库的交互量，结果就是导致了我们的数据库压力不断增加。数据库压力大了会影响小程序的运行效率，从而会影响到用户的体验，为了提升用户的体验，就需要减少数据库的压力，为了减少压力，本系统使用了Redis缓存技术。在第一次查询后，把本次查询到的信息存到Redis，当下一次查询时直接向Redis中取，以此来减少数据库的压力。增加用户浏览时的体验。

### 开发结构
&emsp;本系统使用的是目前市面上主流的B/S体系开发结构，以及通过接口实现微信小程序。在B/S体系结构个中，浏览器是客户端中唯一需要使用的软件，使用浏览器向后端，也就是服务端发送数据，后端响应后，浏览器就会接受到来自后端的数据，这时，浏览器只需要展示对应的数据即可。这样的开发结构有利于统开发和维护，同时提高了系统的可拓展性。
本系统的总体设计的基础框架使用的是MVC模式，MVC是目前常见的一种架构模式，这种架构模式可以降低视图与业务逻辑的双向耦合，系统的逻辑架构图如下图所示。 

![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/bca11b5b-2f01-446f-b5a3-e98ae824c41a)

### 模块设计

![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/6f97c94e-1977-43c5-8dd9-d3e826c0f94f)

### 数据库逻辑设计

![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/c8f55d4c-b51f-467d-99bc-9d0f29bbba84)


***
*微信小程序端页面展示。*

![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/715dc5c3-095c-435e-ad00-261f442d431c)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/6754a7b0-649d-47b0-9b23-e35a119c66e6)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/105cece1-62e0-4f26-ae25-b068dedf7852)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/f961af2c-1b3d-4399-a9da-04e0257b47b2)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/58556bac-c6d9-45cc-9971-e1313aa13bdb)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/50d296ce-1e91-4249-98a7-67b6d3526605)


*后台管理页面展示。*

![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/81ff3de2-3774-4b9a-89e0-44b991d01f26)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/ad04c18d-a08e-4fba-ac0a-54a6e5d41fad)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/e6ee1d08-50cf-4287-8752-be76d6975010)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/22d687bf-d2aa-4521-8dae-fab148965377)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/8b946d78-c610-4836-9f37-41e57578a1a9)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/0efbd25b-d9fe-40ff-959c-8845746d427b)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/d660c450-cc86-4f7b-92c9-38fb3963ff33)
![image](https://github.com/SkyWJN/Health-Management-System/assets/66019283/0d408e1c-4f37-4882-8049-22b2f410061d)



