## Unreleased

(empty)

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
