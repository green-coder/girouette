# Shadow-CLJS Reagent demo

This demonstrates one of the way Girouette can be used in a
Single Page Application (SPA) using Shadow-CLJS and Reagent.

The same would work for a Re-Frame application.

## How it works

### CSS class names in metadata

The source code is using the macro `girouette.tw.reagent/defc`
which adds a set of potentially used CSS class-names in the metadata
attributes of  the program's Clojure vars.

### Shadow-CLJS Compilation Hook

A compilation hook is invoked right after the source code compilation finishes.
It is passed a data structure `build-state` which contains the metadata of all the
Clojure vars in the compiled program. The hook collect the class names stored in
the metadata in the step above, then uses `girouette` and `garden` to generate a
CSS file.

`Shadow-CLJS` has a built-in mechanism to reload CSS file into your webapp which is
running in the browser.

## Launching the webapp

Load modules used by Shadow-CLJS (you only need to do that once):
```shell
npm i
```

Then launch the compiler in watch mode:
```shell
shadow-cljs watch frontend
```

Browse your webapp by clicking on the link displayed by the compiler
after completion of the compilation.

At that point, the front end Clojurescript code and the CSS will be
automatically reloaded in the browser if you change the source code.

## Alternative way to use Girouette

`Girouette` can be run directly inside your frontend application, where
the generation of the CSS and injection in the browser's styles would be
done entirely on the front end without the need to reload the CSS.

This approach does not have a demo yet, feel free to contribute and add one.
