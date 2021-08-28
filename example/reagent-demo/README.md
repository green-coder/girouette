# Reagent demo

This demonstrates one of the way Girouette can be used in a
Single Page Application (SPA) using Reagent.

The same process should work with many other front end frameworks.

## How it works

We use the macro `girouette.core/css` to annotate the CSS class names in the source code
and differentiate them from any other text the source code might contain.

The Girouette Processor tool is then parsing the source code in the background, finds the
CSS class names, and generates the content of `public/style/girouette.css` based on
the CSS class names found.

## Launching the webapp

Load modules used by Shadow-CLJS (you only need to do that once):
```shell
npm i
```

Then launch the compiler in watch mode:
```shell
shadow-cljs watch frontend
```

In parallel, launch Girouette's CSS processor in watch mode:
```shell
clojure -X:girouette-processor
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
