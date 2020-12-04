# Girouette CSS

[![Une girouette](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Girouette_Bateau_Yeu.jpg/360px-Girouette_Bateau_Yeu.jpg)](https://commons.wikimedia.org/wiki/File:Girouette_Bateau_Yeu.jpg)

> Dès que le vent soufflera, je repartira.<br>
> Dès que les vents tourneront, nous nous en allerons.

Girouette is a grammar-based, generative approach to CSS.

## Introduction

This library is a PoC for a new approach to "utility-first" CSS frameworks.

It generates CSS classes and their definition based on a given grammar and
on the CSS class names which the user is using.

This "on demand" generative approach allows to have any combination of
parameters in a CSS class name, while opening the door to the most creative
grammars which a human would want to use to communicate its intent.

## How it works

`Girouette` is using the awesome [`Instaparse`](https://github.com/Engelberg/instaparse)
library for parsing the class names,  and is converting them into the
[`Garden`](https://github.com/noprompt/garden) format.

Its API mainly consists in the function `class-name->garden` which is pretty explicit.

```clojure
(class-name->garden "top-42%")
;=> [".top-42\\%" {:top "42%"}]
```

To have the CSS generated automatically during a build, the user needs to extract
the CSS class names used in his project and feed them to `Girouette`.

How to do this is up to the user, and varies between frontend frameworks.
Some class name extractors and a better tooling integration might be added to
`Girouette` at some point.

## Where we are now

We just started!

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
`GNU General Public License, version 2 with the GNU Classpath Exception`
which is available at https://www.gnu.org/software/classpath/license.html

Copyright (c) Vincent Cantin and contributors.
