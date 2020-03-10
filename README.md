# MobCoinTrophies [![GitHub license](https://img.shields.io/github/license/BGMP/MobCoinTrophies.svg)](https://github.com/BGMP/MobCoinTrophies/blob/master/LICENCE.md)

#### MobCoin-producing trophies plugin

## Prerequisites
* Java 8 or above
* [Maven](http://maven.apache.org/) (Dependency Management)

## Installation
As in any other Maven project, simply clone the repository, and launch your command prompt within it upon decompression:

  > `mvn clean package`

## Usage
#### There only is one command:
* `trophies add {player} {tier_id}`: Gives the specified player a trophy matching the provided tier id.
    
## Notes
* The configuration is static, which means no tiers can/should be added or erased.
* Currently, this plugin only offers support for SuperMobCoins.

