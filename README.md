# watch_users

It is a test task application with two screens.
1. List of users got by Retrofit API call, disolayed with image and name.
2. User info screen, with image, name and email.

Navigation made by Navigation Architecture Component.
API calls made with Retrofit+OkHttp+GSON
Dependency injections made by Kodein
Ability to work offline made by Room (saving/getting information from data base)
For easy image loading used Glide library
To make asynchronous API calls and DB transactions and returning result in proper thread used RxJava2


## Screenshots

<div align="center">
<img src="/img/users.jpeg?raw=true" height="400" alt="Users list" title="Users list"/> <img src="/img/user.jpeg?raw=true" height="400" alt="User info" title="User info"/> 
</div>


## Libraries and technologies used

- [RxJava 2](https://github.com/ReactiveX/RxJava)
- [Kodein](https://github.com/Kodein-Framework/Kodein-DI)
- [Retrofit 2](https://square.github.io/retrofit/)
- [OkHttp](https://github.com/square/okhttp)
- [Glide](https://github.com/bumptech/glide)
- [Room](https://developer.android.com/topic/libraries/architecture/room)
- [Navigation Acrh Comp](https://developer.android.com/guide/navigation)
- [GSON](https://github.com/google/gson)
