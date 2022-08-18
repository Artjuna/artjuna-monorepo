# C22-PS339 [Artjuna]

## About The Project

Artjuna is a social e-commerce platform where customers can create and buy personalized products by combining their own style inspiration with various art styles of artisans with the help of AI technology.


## Team Members

### Team ID : C22-PS339  

| Name                           | Bangkit ID  | Learning Path      | University                          | Contact                                                                                                                                                                                           |
| ------------------------------ | ---------- | ------------------ |  ---------------------------------- |--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Aditya Wardianto          | M7004F0217 | Machine Learning   | Insititut Teknologi Sepuluh Nopember             | <a href="https://www.linkedin.com/in/adityawardianto"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a>                            |
| Fauzia Hafida Rahmah                 | M2010F1100  | Machine Learning   | Universitas Indonesia             | <a href="https://www.linkedin.com/in/fauzia-rahmah-528738175/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a>                            |
| Adi Kelvianto | M2002G0057  | Machine Learning | Institut Teknologi Bandung | <a href="https://www.linkedin.com/in/adi-kelvianto"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a> |
| Dheni Yulia Dinda Pratiwi        | A2008F0874  | Mobile Development | Universitas Gadjah Mada | <a href="https://www.linkedin.com/in/dheni-yulia-dinda-pratiwi-482b591bb/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a>                            |
| Galih Indra Firmansyah                   | A2008F0893  | Mobile Development    | Universitas Gadjah Mada             | <a href="https://www.linkedin.com/in/galihif/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a>                            |
| Nathanael Lauwrent Sudrajat      | C7008F0881  | Cloud Computing    | Universitas Gadjah Mada             | <a href="https://www.linkedin.com/in/nathanael-lauwrent-sudrajat-2465b1197/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a>                            |
<br>

if you are interested in discussing this project issues with us, please contact us or send an email to artjuna.bangkit@gmail.com

# Artjuna Monorepo
[![CD Node API](https://github.com/Artjuna/artjuna-monorepo/actions/workflows/node_api_cd.yml/badge.svg)](https://github.com/Artjuna/artjuna-monorepo/actions/workflows/node_api_cd.yml)
[![CD Python API](https://github.com/Artjuna/artjuna-monorepo/actions/workflows/python_api_cd.yml/badge.svg)](https://github.com/Artjuna/artjuna-monorepo/actions/workflows/python_api_cd.yml)

This is a monorepo for Artjuna, a project created for Bangkit 2022 Capstone Project


<h1 align="center">Artjuna App</h1>



<p align="center">  
🗡️ Artjuna demonstrates modern Android development with Koin, Coroutines, Flow, Jetpack (Room, ViewModel), and Material Design based on MVVM architecture.
</p>
</br>

<p align="center">
<img src="/preview/banner.png"/>
</p>

## Download
Go to the [Here](https://drive.google.com/drive/folders/1bfRFf1mPk6usY0vRu4PMLqQVvebLyESV?usp=sharing) to download the latest APK.

<img src="/preview/demo.gif" align="right" width="320"/>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Koin](https://insert-koin.io/) for dependency injection.
- Jetpack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel & LiveData - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - ViewBinding - Generate binding class to make it easier for interacts with the views from the kotlin class.
  - Room Persistence - Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - Navigation - Make interactions that allow users to navigate across, into, and back out from the different pieces of content within the app
- Architecture
  - MVVM Architecture 
  - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Glide](https://github.com/bumptech/glide) - Loading images from network.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building ripple animation, and CardView.
- [Firebase Auth](https://firebase.google.com/docs/auth) - Handle user authentication with many different credential
- [SDP Scalable Size Unit](https://github.com/intuit/sdp) - Make a scalable views that supports multiple screens and devices
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) - Create circle image
- [Shimmer](https://github.com/facebook/shimmer-android) - Improve user experience by show skeleton items when the content is loading
- [Lottie](https://lottiefiles.com/) - Create animation of an empty state


## MAD Score
<img src="/preview/summary.png"/>

<img src="/preview/kotlin.png"/>

<img src="/preview/jetpack.png"/>

## Architecture
Artjuna is based on the MVVM architecture and the Repository pattern.

<img src="/preview/architecture.png"/>
