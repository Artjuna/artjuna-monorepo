# C22-PS339 [Artjuna] - API Documentation

Artjuna API Documentation  
EndPoint: http://34.101.203.109:8080  
Available endpoints:  

## 1.	/Account
### a.	/Account/getAllAcount
-	Method: GET
-	Request Body:
```json
{
   
}
```
```
Leave it blank.
```
-	Response:
```json
[
    {
        "UserID": "2022U0",
        "Email": "update2@example.com",
        "UserName": "marpaungpurwadi",
        "FullName": "Update Lagi",
        "OriginProvince": "Palembang",
        "OriginCity": "Ogan Ilir",
        "Telephone": "62254151813",
        "Followers": 214,
        "createdAt": "2022-05-30T15:05:14.000Z",
        "IsStore": false
    },
    {
        "UserID": "2022U1",
        "Email": "samiahsafitri@example.org",
        "UserName": "hsirait",
        "FullName": "Jasmani Pangestu",
        "OriginProvince": "Sulawesi Utara",
        "OriginCity": "Minahasa Tenggara",
        "Telephone": "628637525492",
        "Followers": 214,
        "createdAt": "2022-05-30T15:05:14.000Z",
        "IsStore": true
    },
]
```

### b.	/Account/getMyAccountByUserID/{UserID}
-	Method : GET
-	Request Body:
(Leave it blank)
- Request Parameter:
``` 
UserID
```
UserID as STRING: Mandatory  
-	Response: 
```json
[
    {
        "UserID": "2022U0",
        "Email": "update2@example.com",
        "UserName": "marpaungpurwadi",
        "FullName": "Update Lagi",
        "OriginProvince": "Palembang",
        "OriginCity": "Ogan Ilir",
        "Telephone": "62254151813",
        "Followers": 214,
        "createdAt": "2022-05-30T15:05:14.000Z",
        "IsStore": false
    }
]
```

### c.	/Account/getAccountFilter
-	Method : GET
-	Request Body:
```json
{
    "Email": "Adit@mail.com"
}
```
Email as STRING: Not mandatory  

-	Response: 
```json
[
    {
        "UserID": "2022U0",
        "Email": "update2@example.com",
        "UserName": "marpaungpurwadi",
        "FullName": "Update Lagi",
        "OriginProvince": "Palembang",
        "OriginCity": "Ogan Ilir",
        "Telephone": "62254151813",
        "Followers": 214,
        "createdAt": "2022-05-30T15:05:14.000Z",
        "IsStore": 0
    }
]
```
### d.	/Account/addAccount
-	Method : POST
-	Request Body:
```json
{
        
        "Email": "Nathan@mail.com",
        "UserName": "Nathan123",
        "FullName": "Nathan",
        "OriginProvince": "Jawa Barat",
        "OriginCity": "Bandung",
        "Telephone": "abc"
}
```
Email as STRING: Mandatory
UserName as STRING: Mandatory
FullName as STRING: Mandatory
OriginProvince as STRING: Mandatory
OriginCity as STRING: Mandatory
Telephone as STRING: Mandatory
IsStore as BOOLEAN: Not Mandatory, defaultValue: false

-	Response: 
```json
{
    "IsStore": false,
    "UserID": "2022U5695",
    "Email": "Nathan@mail.com",
    "UserName": "Nathan123",
    "FullName": "Nathan",
    "OriginProvince": "Jawa Barat",
    "OriginCity": "Bandung",
    "Followers": 0,
    "Telephone": "abc",
    "createdAt": "2022-06-12T08:04:51.907Z"
}
```
### e.	/Follow/follow
-	Method : POST
-	Request Body:
```json
{
    "UserIDFollowing": "2022U2",
    "UserIDFollowed": "2022U0" 
}
```
UserIDFollowing as STRING: Mandatory
UserIDFollowed as STRING: Mandatory

-	Response: 
```
Following completed
```


## 2.	/Product
### a.	/Product/getAllProduct?page=1&limit=2
-	Method : GET
-	Request Query:
```
page
limit
```

page as INTEGER: Mandatory
limit as INTEGER: Mandatory

-	Response: 
```json
{
    "next": {
        "page": 2,
        "limit": 2
    },
    "results": [
        {
            "ProductID": "2022P0",
            "UserID": "2022U597",
            "ProductName": "Ukiran Kayu Saragih",
            "Category": "Ukiran Kayu",
            "Province": "Sulawesi Tengah",
            "City": "Poso",
            "Description": "At excepturi velit provident. Aut quas tenetur qui. Ullam labore quas suscipit perferendis itaque.",
            "Price": 2045000,
            "Image": "/1654275664335-random_image.png",
            "HasSeen": 487,
            "createdAt": "2022-06-03T17:01:04.000Z",
            "FullName": "Ilsa Kuswandari"
        },
        {
            "ProductID": "2022P1",
            "UserID": "2022U4699",
            "ProductName": "Patung Batu Nashiruddin",
            "Category": "Patung Batu",
            "Province": "Lampung",
            "City": "Way Kanan",
            "Description": "Debitis at eos quod temporibus quae. Nesciunt non eveniet vel eaque voluptate corporis. Aliquid iusto corporis itaque magnam consectetur.",
            "Price": 1430000,
            "Image": "/1654275665520-random_image.png",
            "HasSeen": 537,
            "createdAt": "2022-06-03T17:01:05.000Z",
            "FullName": "Patricia Purnawati"
        }
    ]
}
```

### b.	/Product/getProductFilter?UserID=2022U3777
-	Method : GET
-	Request Body:
(leave it blank)
- Request Query:
```
UserID
ProductID
ProductName
Category
Province
City
Price
```
UserID as STRING: Not Mndatory
ProductID as STRING: Not Mandatory
ProductName as STRING: Not Mandatory
Category as STRING: Not Mandatory
Province as STRING: Not Mandatory
City as STRING: Not Mandatory
Price as INTEGER: Not Mandatory

-	Response:
```json
[
    {
        "ProductID": "2022P1012",
        "UserID": "2022U3777",
        "ProductName": "Lukisan Tradisional Hassanah",
        "Category": "Lukisan Tradisional",
        "Province": "Kalimantan Utara",
        "City": "Nunukan",
        "Description": "Quaerat explicabo magni laudantium delectus. Perferendis in nihil blanditiis nesciunt. Distinctio facilis facilis est ducimus temporibus ut.",
        "Price": 454000,
        "Image": "/1654279243961-random_image.png",
        "HasSeen": 404,
        "createdAt": "2022-06-03T18:00:43.000Z"
    }
]
```

### c.	/Product/getAllProductCategory
-	Method: GET
-	Request Body: (Leave it blank)
-	Response:
```json
[
    {
        "Category": "Ukiran Kayu"
    },
    {
        "Category": "Patung Batu"
    },
    {
        "Category": "Kain Batik"
    },
    {
        "Category": "Ukiran Kayu"
    },
    {
        "Category": "Mainan Tradisional"
    },
    {
        "Category": "Perabotan Kayu"
    }
]
```

### d.	/Product/addProcut
-	Method: POST
-	Request Body:
```form-data
UserID
ProductName
Category
Province
City
Description
Price
Image
```
Username: Mandatory
UserID as STRING: Mandatory
ProductName as STRING: Mandatory
Category as STRING: Mandatory
Province as STRING: Mandatory
City as STRING: Mandatory
Description as STRING: Mandatory
Price as INTEGER: Mandatory
Iamge as FILE: Mandatory

-	Response:
```
Insert Data Success
```

### e.	/Product/seen
-	Method: POST
-	Request Body:
```json
{
    "UserID": "2022U1000",
    "ProductID": "2022P0"
}
```
UserID as STRING: Mandatory
ProdutID as STRING: Mandatory

-	Response
```
seen added
```

### f. /Product/updateProduct
-	Method: PUT
-	Request Body:
```json
{
    "ProductID": "2022P7",
    "ProductName": "Baju hijau"
}
```
ProductID as STRING: Mandatory
ProductName as STRING: Not Mandatory
Description as STRING Not Mandatory
Province as STRING: Not Mandatory
City as STRING: Not Mandatory
Price as INTEGER: Not Mandatory

-	Response
```
update data success
```
