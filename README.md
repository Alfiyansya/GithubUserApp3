# GithubUserApp3
Before I explain how this application works and submission checklist, I want to say **thank you** to **Dicoding Indonesia** for providing "Belajar Fundamental Aplikasi Android" class and **thank you** to **Dicoding Reviewer** for approving my third submission.
### How the app works
1. This app will be displayed a list of Github users from Github REST API
2. If an app user clicks on one of the Github users from the list, the details of the Github user that was clicked will be displayed
3. Users can search the username of any Github users you want, and then data will be displayed in screen
4. If an app user clicks on any of the Github users from the list of username previously searched for, the details of the clicked Github users will be displayed
5. User can add their favorite Github users to the list of favorite Github Users
6. If the app user wants to see their favorite Github users, the user can go to the Github user's favorite list activity
7. Users can view details of their favorite Github users by clicking directly on the cardview in the list of github user favorite activity
8. Users can switch to Dark mode by going to the setting activity and clicking the switch button to Dark mode

### Prerequisites

Before running this app, you need to add your Github Personal Access Token, in your `Build.gradle(Module ..)` file:

```yaml
buildConfigField "String","API_TOKEN","PUT YOUR API KEY IN HERE"
```
### Demo Apps
<p align="center">
    <img src="demo apps/main.gif"
        alt="Demo Apps1"    
        style="margin-right: 8px;"    
        width="230" />
    <img src="demo apps/search.gif"
        alt="Demo Apps2"    
        style="margin-right: 8px;"    
        width="230" />
    <img src="demo apps/favorite.gif"
        alt="Demo Apps2"    
        style="margin-right: 8px;"    
        width="230" />
    <img src="demo apps/dark mode.gif"
        alt="Demo Apps2"    
        style="margin-right: 8px;"    
        width="230" />
</p>

### Submission CheckList
- Aplikasi bisa menambah user ke daftar favorite.
- Aplikasi bisa menghapus user dari daftar favorite.
- Halaman yang menampilkan daftar user favorite.
- Terdapat pengaturan untuk mengganti tema.
- Mempertahankan semua fitur aplikasi dan komponen yang digunakan pada Submission 2.

#### Reviewer Rating 
:star: :star: :star: :star: :star:
### Dependencies :
- [Lifecycle & Livedata](https://developer.android.com/jetpack/androidx/releases/lifecycle)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Retrofit 2](https://square.github.io/retrofit/)    
- [OkHttp 3](https://square.github.io/okhttp/)    
- [Glide](https://github.com/bumptech/glide)    
- [AndroidX](https://mvnrepository.com/artifact/androidx)
- [KotlinX Coroutines](https://developer.android.com/kotlin/coroutines)
- [Circle ImageView](https://github.com/hdodenhof/CircleImageView)
- [Lottie Android](https://github.com/airbnb/lottie-android)
- [RoomDatabase](https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
