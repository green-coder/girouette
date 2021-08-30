## Unreleased

### Fixed

- (issue #72) Added missing cases for the `max-width` rule. Thanks to `joe-loco` for the bug report.
- (issue #71) The media queries are now coming after the non-media queries in the style file.
- (PR #73) Fixed the line height on text-5xl ~ text-9xl. Thanks to `jeremS` for the PR.

## V0.0.4

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
