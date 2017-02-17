# kir
KIR is an isomorphic Web Micro Framework for the [Kotlin](https://kotlinlang.org/) platform, influenced by [RedStone](http://redstonedart.org/) and [Meteor](https://www.meteor.com/).

#### Example
Kir allows to use simple annotations to create the endpoints.

```kotlin
package helloworld

import com.github.jepemo.kir.web.*

@Route("/")
fun hello () = "Hello World"

@Route("/hello/:to")
fun helloTo(to: String) = "Hello $to!"

fun main(args : Array<String>) {
    App.start();
}
```

#### Installation

Todo
 