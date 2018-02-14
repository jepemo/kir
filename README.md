# kir
KIR is an isomorphic Web Micro Framework for the [Kotlin](https://kotlinlang.org/) platform, influenced by [RedStone](http://redstonedart.org/) and [Meteor](https://www.meteor.com/).

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

 