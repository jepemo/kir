# kir
KIR is an isomorphic Web Micro Framework for the [Kotlin](https://kotlinlang.org/) platform, influenced by [RedStone](http://redstonedart.org/) and [Meteor](https://www.meteor.com/) & [React](https://reactjs.org/).

- [Example](#example)
- [Installation](#installation)
- [Documentation](#documentation)
- [More examples](#more-examples)

### Example
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

### Installation

#### From source:

Install *Gradle*

In Ubuntu:
```bash
sudo apt-get install gradle
```

Download source

```bash
git clone https://github.com/jepemo/kir.git
cd kir
```

Compile and install kir library

```bash
make install
```

Now you can use the *kb* command. Type:
```bash
kb help
```

### Documentation

* [Intro](doc/intro.md)
* [Routes](doc/routes.md)
* [kb - Tutorial](doc/kb_tutorial.md)
* [kb - Reference](doc/kb_reference.md)

### More examples
* [Views](https://github.com/jepemo/kir/blob/master/kir-examples/src/main/kotlin/com/github/jepemo/kir/examples/web/Views.kt)
* [Rest Service](https://github.com/jepemo/kir/blob/master/kir-examples/src/main/kotlin/com/github/jepemo/kir/examples/web/SimpleRest.kt)
* [Web Component](https://github.com/jepemo/kir/blob/master/kir-examples/src/main/kotlin/com/github/jepemo/kir/examples/dom/SimpleComponent.kt)

 
