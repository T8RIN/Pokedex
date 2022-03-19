<h1 align="center">Pokedex</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/t8rin/Pokedex/actions"><img alt="Build Status" src="https://github.com/skydoves/Pokedex/workflows/Android%20CI/badge.svg"/></a> 
  <a href="https://github.com/t8rin"><img alt="Profile" src="https://img.shields.io/badge/Github-t8rin-blue?logo=github"/></a> 
</p>

<p align="center">  
Simple Pokedex app that allows you to easily search and look around for your favourite pokemons and theirs statistics
</p>
</br>

<p align="center">
<img src="blob/preview/intro.png"/>
</p>

## Download
Go to the [Releases](https://github.com/t8rin/Pokedex/releases) to download the latest APK.


## Tech stack & Open-source libraries
- Minimum SDK level 21

- [Kotlin](https://kotlinlang.org/) based 

- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) to work with internet and move tasks to a secondary thread

- [Hilt](https://dagger.dev/hilt/) for dependency injection.

- JetPack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - ViewBinding - Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
  - Palette - to generate color palette based on bitmap
  - RecyclerView - for displaying large sets of data in UI while minimizing memory usage.
  
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository Pattern

- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.

- [Coil](https://github.com/coil-kt/coil) - loading images.

- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.

- [ProgressView](https://github.com/skydoves/progressview) - A polished and flexible ProgressView, fully customizable with animations.

# License
```xml
Designed and developed by 2021 T8RIN

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
