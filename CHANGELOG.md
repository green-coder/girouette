## Unreleased

## v0.0.10

## Fixed

- (PR #93) Fixed an inversion left-right in the `divide-x-..` classes. Thanks to `andrei-zhidkov` for the fix.
- (PR #94) Fixes the format of the CSS colors in `box-shadow`. Thanks to `zolazhou` for the fix.

## v0.0.9

## Fixed

- (PR #92) Fixed the CSS Selectors broken in `v0.0.8`. Thanks to `zolazhou`.

## v0.0.8

## Added

- (PR #83) Compatibility with Tailwind `v3.0.23`, except for the Tailwind-styled "arbitrary values" specified between `[` and `]`.
  Thanks to `jamesnvc` for this large contribution.
- Added Tailwind v3's "colors" and "extended colors".
- Added fields `:since-version` and `:removed-in-version` on Girouette components.
- Added a function to filter components based on a version value. Users can
  select components for the version they want, e.g. `[:tw 2]` or `[:tw 3]`,
  making Girouette (hope)fully backward compatible.
- (Issue #91) Added a more recent v3.0.24 version of Preflight (v3.0.24), selectable via the settings of the Girouette processor.

## Fixed

- (PR #85) Implemented `--gi-divide-*-reverse`.
  Thanks to `flyingmachine` for the bug report and fix.
- Cljdoc is analyzing Girouette correctly again. Thanks to `ribelo` and `lread` for their help.

## Changed

- (PR #86) Swap hawk for beholder. It results in a faster response of the CSS processor tool while in the watch mode.
  Thanks to `dpassen` for the contribution.
- Updated the example project to use the Girouette API setup compatible with Tailwind v3.

## Breaking changes

Some symbols have been renamed to better reflect the multiple versions supported by Girouette:
- `girouette.tw.color/default-color-map` -> `girouette.tw.color/tw-v2-colors`
- `girouette.tw.default-api/default-components` -> `girouette.tw.default-api/all-tw-components`
- `girouette.tw.default-api/class-name->garden` -> `girouette.tw.default-api/tw-v2-class-name->garden`
- `girouette.tw.typography/default-font-family-map` -> `girouette.tw.typography/tw-v2-font-family-map`
- `girouette.tw.preflight/preflight` -> `girouette.tw.preflight/preflight-v2_0_3`

The processor's params have changed (see the [example project](https://github.com/green-coder/girouette/blob/fd0f7cbb017ea5a989c5ce01149c67896aaca977/example/reagent-demo/deps.edn)):
- `garden-fn` is no longer optional, you need to provide a qualified symbol.
- `preflight?` was replaced by `base-css-rules`, it takes a vector of qualified symbols.

## v0.0.7

### Fixed

Thanks to `jamesnvc` for the following fixes:
- (PR #76) Escape octothorpe character in class name.
- (PR #77) Fix `.invisible` class.
- (PR #80) Fix the rule `:max-width`.
- Fix bug in the processor tool when parsing CLJS files containing character literals.

- Fixed and simplified the function `girouette.util/rule-comparator`.

## v0.0.6

### Changed

- (PR #74) Changed the garden data output for components like `space-x-2` to make it easier to be processed by libraries like Ornament.
  The change won't affect most end users as the new garden data has the same effect, CSS-wise.
  Big thank to `Vynlar` for this cross-project contribution.

## v0.0.5

### Added

- `girouette.garden.util/rule-comparator` can be used for ordering the garden rules which are output by `class-name->garden`.
  Related to issue #71 and PR #66.

### Fixed

- (issue #72) Added missing cases for the `max-width` rule. Thanks to `joe-loco` for the bug report.
- (issue #71) The media queries are now coming after the non-media queries in the style file. Thanks to `joe-loco` for the bug report.
- (PR #73) Fixed the line height on text-5xl ~ text-9xl. Thanks to `jeremS` for the PR.

## v0.0.4

### Added

- SCSS's @apply equivalent (issue #35 and PR #64)
- Partial ordering between different rules (commit e0e9ab34d2bdefb7b7289ab15e06a3e243dc230c)
- The return value of `class-name->garden` now has some metadata:
  - It's useful during the development of the Girouette components.
  - The metadata also contains the information about how the garden rules should be ordered in a CSS file.
- Added `:dry-run?` flag in the inputs of the processor.

### Fixed

- Fixed the state variant "first", "last", "odd" and "even" (PR #62)
- Fixed a typo in Preflight (PR #63)
- Fixed typo in :font-variant-numeric rule (PR #68)
- Fixed typo in :divide-width rule
- Fixed minor bugs in the processor (issues #60 and #61)

## v0.0.3

### Added

- Added support for HSL colors.
- Made the colors easy to customize. Updated the demo.
- Made the font-family easy to customize.
- Extended the rule `:line-height` to work with any number.
- `make-api` does no longer need to have specified colors or font families to work.

### Fixed

- (issue #55) The "display" rule is now working correctly when used with a variant like "sm:".
  Thanks to @jacobobryant for reporting it.

## v0.0.2

### Added

- Preflight CSS from Tailwind was converted into Garden, to be used as a base by the processor tool.
- "font-size-..." Girouette component.
- "line-height-..." Girouette component.

### Changed

- Source code clean up for Cljdoc.

### Fixed

- "min-width" and "max-width" were not implemented correctly.
- "font-weight" was not implemented correctly.

## v0.0.1

Initial release
