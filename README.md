## Girouette [![CircleCI](https://circleci.com/gh/green-coder/girouette.svg?style=svg)](https://circleci.com/gh/green-coder/girouette)

[![Clojars Project](https://img.shields.io/clojars/v/girouette.svg)](https://clojars.org/girouette)
[![Cljdoc badge](https://cljdoc.org/badge/girouette/girouette)](https://cljdoc.org/d/girouette/girouette/CURRENT)
[![Project chat](https://img.shields.io/badge/slack-join_chat-brightgreen.svg)](https://clojurians.slack.com/archives/C01J8H2VD97)
[![Clojars download_badge](https://img.shields.io/clojars/dt/girouette?color=opal)](https://clojars.org/girouette)


[![Une girouette](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Girouette_Bateau_Yeu.jpg/360px-Girouette_Bateau_Yeu.jpg)](https://commons.wikimedia.org/wiki/File:Girouette_Bateau_Yeu.jpg)

> Dès que le vent soufflera, je repartira.<br>
> Dès que les vents tourneront, nous nous en allerons.

Girouette is a grammar-based, generative approach to CSS.
It translates a classname into a Garden data structure representing CSS.

```clojure
(class-name->garden "w-10")
; => [".w-10" {:width "2.5rem"}]
```

Girouette also makes it easy to use your own grammar rules to generate anything
you may dream of.

## Introduction

CSS libraries like Tailwind and Tachyons provide quantities of predefined class names,
hoping to cover most of the needs of their users. But because of combinatory explosion,
they cannot provide all the class names users will ever need, in which case the users will
have to hack their way with config files and/or handwritten CSS.
When releasing for production, the unused CSS classes are removed from the CSS files using
tools like PurgeCSS.

Girouette is taking the opposite approach where we first look at which class names are used,
and from their names and a grammar associated with some generation rules, the corresponding
CSS content is created.

This "on demand" generative approach allows to have any combination of parameters in a CSS
class name, while opening the door to the most creative grammars which a human would want
to use to communicate its intent.

### Documentation & Resources

Girouette supports [all the classes available in Tailwind 2.0.2](https://tailwindcss.com/docs)

Presentation @ the Bay Area Clojure Meetup:
- [The slides](https://app.pitch.com/app/presentation/a760be33-4a5b-4e73-bd25-07387cd197dc/7282e9fa-8789-43bc-8b2d-eaec38711d98)
- [Video recording](https://www.youtube.com/watch?v=Tnv6SvZM6tg)

The project has example projects in `example/`:
- A simple [demo project using Reagent](example/reagent-demo).

## How it works

`Girouette` is using the awesome [`Instaparse`](https://github.com/Engelberg/instaparse)
library for parsing the class names, and is converting them into the
[`Garden`](https://github.com/noprompt/garden) format.

Its API mainly consists in the function `class-name->garden` which is pretty explicit.

```clojure
(class-name->garden "w-42%")
;=> [".w-42\\%" {:width "42%"}]
```

You can use `Girouette processor tool` to extract the CSS class names from
your source code and generate the CSS in real time as you develop.

See the [demo project](example/reagent-demo) for more information.


## Advantages of this approach

With the right Girouette components in place, any parameters can be combined
in class names without leaving your usual REPL workflow.

### Large range on numbers

No need to stop what you are doing and to modify some config files just because
`mx-13` is not supported by default while `mx-12` is.

Any color can be represented directly in class names,
like `rgba-f59d` or `rgba-ff5599dd`.

### Limitless class name descriptiveness

It is possible to create grammars which support very long class names.

```clojure
;; Example of class name:
"bg-gradient-to-right-red-orange-yellow-green-blue-purple"

;; Instaparse rule:
"bg-gradient = <'bg-gradient-to-'> gradient-direction (<'-'> color)+
 gradient-direction = 'top' | 'right' | 'bottom' | 'left' | angle
 color = ...
 "
```

## Contribute

Contributions are very welcome.

## License terms

This project is distributed under the `MIT License`, which is available at
https://opensource.org/licenses/MIT

Copyright (c) Vincent Cantin and contributors.
