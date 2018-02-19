# Hexameter

Hexameter is a hexagonal grid library. The motivation behind it is to have
an optimized, simple and usable library for drawing hexagonal grids without
being tied to any GUI framework.

This means that you can use Hexameter on Android, your backend or your desktop app.

There is a REST-based web example which you can tinker with [here][herokurestlink]. *(not recommended, this is under rewrite)*

You can also check out the hexameter.example.swt project [here][exampleprojectslink].

Hexameter currently supports a maximum grid size of 1000 * 1000 (1.000.000 cells) with the default implementation but
you can provide your own storage implementation to alleviate this limitation.

Note that this library uses [RxJava][rxlink]. You should familiarize yourself with the basics (nothing more needed) in order
to use it effectively. If you don't want to learn [RxJava][rxlink] don't worry the code examples below can be used without
diving into the details.

[![][travis img]][travis]
[![][maven img]][maven]
[![][codecov img]][codecov]
[![][license img]][license]
[![][ocbackers img]][ocbackers]
[![][ocsponsors img]][ocsponsors]

## Fork
This fork adds an array of vertex-indices (as of [eeb9b](https://github.com/HoldYourWaffle/Hexameter/commit/eeb9b84c0b3841e2f973a1682d64a4350b8f2876)) that doesn't really make any sense without the [libGDX](https://github.com/libgdx/libgdx) game library.

## Getting started

This library uses [Amit's guide to hexagonal grids][amitlink]. The coordinate system used by this library is the Cubic coordinate system.
Please check [here][cubecoords] for further details.

Hexagonal grids come in **flat topped** and **pointy topped** shapes. The grid can have several layouts:
 - Hexagonal: the width and height of a this layout has to be equal and both have to be an odd number.
 - Triangular: the width and height of a this layout has to be equal.
 - Rectangular: no special rules
 - Trapezoid: no special rules

All layouts have *with* and *height* values of at least **1**.
You can consult [HexagonalGridLayout][hexgridlayout] if you need further details.

This library is not tied to any GUI implementation. All operations provided by the [API][api] are working using the most abstract concept possible.

## Basic usage

### Maven dependency
Let's start by adding Hexameter as a Maven dependency to your project:

    <dependency>
	    <groupId>org.codetome.hexameter</groupId>
	    <artifactId>hexameter.core</artifactId>
	    <version>2017.1.0</version>
    </dependency>

You can also use Gradle:

    'org.codetome.hexameter:hexameter.core:2017.1.0'


### Creating a grid

You can use the [HexagonalGridBuilder][hexgridbuilder] from the API package to create a [HexagonalGrid][hexgrid]:

    import org.codetome.hexameter.core.api.HexagonalGridLayout;
    import org.codetome.hexameter.core.api.HexagonOrientation;
    import org.codetome.hexameter.core.api.HexagonalGrid;
    import org.codetome.hexameter.core.api.HexagonalGridBuilder;

    import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;
    import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;
    // ...
    private static final int GRID_HEIGHT = 9;
    private static final int GRID_WIDTH = 9;
    private static final HexagonalGridLayout GRID_LAYOUT = RECTANGULAR;
    private static final HexagonOrientation ORIENTATION = FLAT_TOP;
    private static final double RADIUS = 30;

    // ...
    HexagonalGriBuilder builder = new HexagonalGridBuilder()
        .setGridHeight(GRID_HEIGHT)
        .setGridWidth(GRID_WIDTH)
        .setGridLayout(GRID_LAYOUT)
        .setOrientation(ORIENTATION)
        .setRadius(RADIUS);

    HexagonalGrid grid = builder.build();

You can also use the [HexagonalGridBuilder][hexgridbuilder] to create a [HexagonalGridCalculator][hexgridcalc] for you which supports advanced operations
on [HexagonalGrid][hexgrid]s:

    import org.codetome.hexameter.core.api.HexagonalGridCalculator;
    // ...
    HexagonalGridCalculator calculator = builder.buildCalculatorFor(grid);

### Drawing a grid

You can fetch the `Hexagon`s stored on a grid using the `getHexagons` method:

    Observable<Hexagon> hexagons = grid.getHexagons();

After that you can iterate over all the `Point`s of your `Hexagon`s:

    hexagons.forEach(new Action1<Hexagon>() {
        @Override
        public void call(Hexagon hexagon) {
            for (Point point : hexagon.getPoints()) {
                // do what you want
            }
        }
    });


Note that each `Point` represents a coordinate in 2D space. You can use them for drawing.

### Manipulating your grid

There are basically only one operation for manipulating your data on the grid:
The `Hexagon#setSatelliteData(T data)` operation with which you can add your own arbitrary
data to a `Hexagon` object. This means that once created a `HexagonalGrid` is immutable apart from the
satellite data you add.

There is also a `HexagonalGrid#clearSatelliteData()` method for clearing all satellite data from your grid.

The implementation of the `HexagonalGrid` is lazy. This means that it only stores data which is absolutely necessary
to keep in memory (the coordinates and your satellite data). Everything else is generated on the fly. The only limiting
factor of a grid at the moment is the coordinates (which consume memory) and the satellite data.

### GUI example:

You can find a simple GUI example in the `hexameter.example.swt` project. Run it by doing the following steps.

1. Clone the project: `git clone git@github.com:Hexworks/hexameter.example.swt.git`
2. cd to the newly created `hexameter.example.swt` folder: `cd hexameter.example.swt/`
3. build the project: `./gradlew clean build` (or `gradlew clean build` on Windows)
4. run the created uberjar: `java -jar build/libs/hexameter.example.swt.jar`


### Supported operations
 - Querying the characteristics of the `HexagonGrid`
 - Fetching all the `Hexagon` objects from the grid
 - Getting a subset of Hexagons (using cube or offset coordinate range) from the grid
 - Checking whether a Hexagon is on a grid or not
 - Getting a `Hexagon` by its grid coordinate (cube)
 - Getting a `Hexagon` by its pixel coordinate
 - Getting the neighbors of a hexagon (also by index)

### Advanced operations
 - Calculating the distance between two `Hexagon`s
 - Calculating the movement range from a `Hexagon` to an other
 - Rotating a `Hexagon`
 - Calculating a ring from a `Hexagon`
 - Draw a line from a `Hexagon` to an other
 - Checking visibility of a `Hexagon` from an other
 - Adding custom data to a Hexagon
 - Clearing all custom data from the HexagonalGrid

Check these interfaces for more details:

- [HexagonalGridBuilder][hexgridbuilder]
- [HexagonalGrid][hexgrid]
- [HexagonalGridCalculator][hexgridcalc]
- [Hexagon][hex]

### Usage tips
 - You can add satellite data (any arbitrary data you have) to a `Hexagon`. By implementing the [`SatelliteData`][satdatlink] interface you gain operations like visibility checking
 - Hexameter comes with a sensible default implementation of [`SatelliteData`][satdatlink] so if you don't want to add extra data you can use [`DefaultSatelliteData`][defsatdatlink].
 - You can use your own implementation of [`HexagonDataStorage`][hexdatstorlink] for storing your `Hexagon`s
 - Hexameter comes with a sensible [`DefaultHexagonDataStorage`][defhexdatstorlink] implementation which stores all data in memory
 - You don't have to fetch all `Hexagon` objects by using the `getHexagons` method. You can query `Hexagon`s by a range using offset or cube coordinates

## Road map:
 - Path finding with obstacles  (blocking movement)
 - Movement range with obstacles and movement cost calculation
 - Android example

## Contributors

This project exists thanks to all the people who contribute. [[Contribute](CONTRIBUTING.md)].
<a href="https://github.com/Hexworks/hexameter/graphs/contributors"><img src="https://opencollective.com/Hexworks/contributors.svg?width=890" /></a>


## Backers

Thank you to all our backers! 🙏 [[Become a backer](https://opencollective.com/Hexworks#backer)]

<a href="https://opencollective.com/Hexworks#backers" target="_blank"><img src="https://opencollective.com/Hexworks/backers.svg?width=890"></a>


## Sponsors

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/Hexworks#sponsor)]

<a href="https://opencollective.com/Hexworks/sponsor/0/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/0/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/1/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/1/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/2/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/2/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/3/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/3/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/4/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/4/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/5/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/5/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/6/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/6/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/7/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/7/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/8/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/8/avatar.svg"></a>
<a href="https://opencollective.com/Hexworks/sponsor/9/website" target="_blank"><img src="https://opencollective.com/Hexworks/sponsor/9/avatar.svg"></a>



## License
Hexameter is made available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).

## Credits
Hexameter is created and maintained by Adam Arold

*I'm open to suggestions, feel free to comment or to send me a message.
Pull requests are also welcome!*


[ocbackers]: #backers
[ocbackers img]:https://opencollective.com/Hexworks/backers/badge.svg
[ocsponsors]: #sponsors
[ocsponsors img]:https://opencollective.com/Hexworks/sponsors/badge.svg

[travis]:https://travis-ci.org/Hexworks/hexameter
[travis img]:https://api.travis-ci.org/Hexworks/hexameter.svg?branch=master

[maven]:http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.codetome.hexameter%22%20AND%20a%3A%22hexameter.core%22
[maven img]:https://maven-badges.herokuapp.com/maven-central/org.codetome.hexameter/hexameter.core/badge.svg

[codecov img]:https://codecov.io/github/Hexworks/hexameter/coverage.svg?branch=master
[codecov]:https://codecov.io/github/Hexworks/hexameter?branch=master

[license]:https://github.com/Hexworks/hexameter/blob/master/LICENSE
[license img]:https://img.shields.io/badge/License-MIT-green.svg

[amitlink]:http://www.redblobgames.com/grids/hexagons/
[cubecoords]:http://www.redblobgames.com/grids/hexagons/#coordinates
[herokurestlink]:http://hexameter-rest-example.herokuapp.com/
[exampleprojectslink]:https://github.com/Hexworks/hexameter.example.swt

[hexgridlayout]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/HexagonalGridLayout.java
[hexgridbuilder]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/HexagonalGridBuilder.java
[api]:https://github.com/Hexworks/hexameter/tree/master/src/main/java/org/codetome/hexameter/core/api
[hexgrid]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/HexagonalGrid.java
[hexgridcalc]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/HexagonalGridCalculator.java
[hex]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/Hexagon.java
[rxlink]:https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava
[satdatlink]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/contract/SatelliteData.java
[defsatdatlink]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/defaults/DefaultSatelliteData.java
[hexdatstorlink]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/contract/HexagonDataStorage.java
[defhexdatstorlink]:https://github.com/Hexworks/hexameter/blob/master/src/main/java/org/codetome/hexameter/core/api/defaults/DefaultHexagonDataStorage.java
