## Girouette [![CircleCI](https://circleci.com/gh/green-coder/girouette.svg?style=svg)](https://circleci.com/gh/green-coder/girouette)

[![Clojars Project](https://img.shields.io/clojars/v/girouette.svg)](https://clojars.org/girouette)
[![Cljdoc badge](https://cljdoc.org/badge/girouette/girouette)](https://cljdoc.org/d/girouette/girouette/CURRENT)
[![Project chat](https://img.shields.io/badge/slack-join_chat-brightgreen.svg)](https://clojurians.slack.com/archives/C01J8H2VD97)
[![Clojars download_badge](https://img.shields.io/clojars/dt/girouette?color=opal)](https://clojars.org/girouette)


[![Une girouette](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Girouette_Bateau_Yeu.jpg/360px-Girouette_Bateau_Yeu.jpg)](https://commons.wikimedia.org/wiki/File:Girouette_Bateau_Yeu.jpg)

> Dès que le vent soufflera, je repartira.<br>
> Dès que les vents tourneront, nous nous en allerons.

Girouette is a grammar-based, generative approach to CSS.

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

## How it works

`Girouette` is using the awesome [`Instaparse`](https://github.com/Engelberg/instaparse)
library for parsing the class names, and is converting them into the
[`Garden`](https://github.com/noprompt/garden) format.

Its API mainly consists in the function `class-name->garden` which is pretty explicit.

```clojure
(class-name->garden "top-42%")
;=> [".top-42\\%" {:top "42%"}]
```

To have the CSS generated automatically during a build, the user needs to extract the
CSS class names used in his project and feed them to `Girouette`.

How to do this is up to the user, and varies between frontend frameworks.
Some class name extractors and a better tooling integration will be added to the project
at some point.

## Advantages of this approach

Any parameters can be combined in the class names without leaving your usual REPL workflow,
as long as a supporting grammar was previously created.

### Large range on numbers

No need to stop what you are doing and to modify some config files just because
`mx-13` is not supported by default while `mx-12` is.

Any color can be represented directly in class names like `rgba-f59d`, `rgba-f05090d0`,
or with any variant applied like `rgba-f59d-10%-darker`

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

## Where we are now

We just started! Still a PoC at the moment.

`Girouette` can use any grammar. To help the community adoption,
an effort is currently being made to create a grammar which is compatible
with the class names used by [`Tailwind CSS`](https://tailwindcss.com/).

Once compatibility is achieved, the goal is to leverage the approach of
`Girouette` and develop its grammar into a more expressive one.

If enough people are interested (and contribute), we may as well have a
separate grammar  which is compatible with [`Tachyons`](https://tachyons.io/).

## Contribution first

This project is "contribution first", it means that it will only be completed if
people contribute.

The initial codebase was made specifically to make contributing easy. Contributors
are very welcome.

All contributions you make to this project have to be your own, and  in agreement
with the license terms below.

## License terms

This project is distributed under the `Eclipse Public License version 2.0`,
with a Secondary License
`GNU General Public License, version 3 with the GNU Classpath Exception`
which is available at https://www.gnu.org/software/classpath/license.html

Copyright (c) Vincent Cantin and contributors.
