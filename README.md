    This project is for university course
# Java Steam Market Checker
This application is a JAVA backend built on Spring Boot Technology, using MVC-pattern and a simple front-end with Thymeleaf and a little JavaScript, to parse item skins and containers from popular game **Counter-Strike 2** and check their actual price using Steam user market API.

# Userguide
## API Endpoints

In the root URL '/' you can find another user guide on how to use this applications endpoints
There are 4 API endpoint that return JSON at get request

|                |Parameters                     |Result                         |
|----------------|-------------------------------|-----------------------------|
|/api/search/    |`{"weaponName", "skinName", "limiter"}`|A number of [limiter] weapons that closely resemble type of[weaponName] and [skinName] will be returned on page            |
|/api/search/    |`{"findType", "containerName", "limiter"}`|Depending on the [findType] either "All", "One" or "Several" this endpoint will return: All - returns all containers in the game, One - returns a specific container by [containerName], Several - returns last [limiter] containers.            |
|/api/search/allcases          |`None`|Returns history of API calls for cases that is stored in DB|
|/api/search/allskins          |`None`|Returns history of API calls for skins that is stored in DB|


## Visual Endpoints
There are two front-end endpoints: 
**/search/cases**
**/search/skins**
There you can search for items using same rules as listed in API endpoints.
You can check items with a checkbox and make a call to Steam API for their price
**IMPORTANT!**
**Do not select more than 3 items with checkbox simultaneously, not make calls earlier than 5 seconds apart**
Steam API has a limit of 20 calls per minute and 1000 calls per day.

After receiving a response, EXCEL spreadsheet can be generated in root directory of the project.
