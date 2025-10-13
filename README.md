# Farmer's Attributes
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/1355713?style=flat&logo=CurseForge&logoColor=F16436&label=CurseForge&color=F16436)](https://www.curseforge.com/minecraft/mc-mods/farmers-attributes)
[![ModRinth Downloads](https://img.shields.io/modrinth/dt/farmers_attributes?style=flat&logo=modrinth&logoColor=%2300AF5C&label=Modrinth&color=%2300AF5C)](https://modrinth.com/mod/farmers_attributes)
[![Minecraft Versions](https://img.shields.io/badge/MC-1.21.1-green?style=flat&logo=minecraft&logoColor=white)](https://www.minecraft.net/ja-jp/about-minecraft)
[![Server Side](https://img.shields.io/badge/Side-Server%26Client-orange?style=flat)](#)
[![License](https://img.shields.io/github/license/medi-torimorta/farmers-attributes?style=flat&color=purple)](https://github.com/medi-torimorta/farmers-attributes/?tab=MIT-1-ov-file#)

This mod aims to enhance farming experience in minecraft by adding multiple player attributes related to farming and cooking.

## Usage
Farmland protection and right-click harvesting/replanting can be turned on for all players in the config.  
The other features are best used in conbination with other mods that make use of the attributes, as this mod doesn't add a way to increase the player attributes by itself.  
You can also use commands to set your attributes.  
ex.  
`/attribute @s farmers_attributes:crouch_bonemeal_chance base set 0.5`

## Contents
### Attributes

**anti_farmland_trampling**  
If True (non-zero), prevents farmlands from being trampled by the player.  

**easy_harvest**  
If True (non-zero), allows for harvesting crops by right clicking, sending the drops directly to the player's inventory. Uses seeds from the drop content or the player's inventory for replanting.  
Also works on kelps and sugarcanes (and similar modded plants) leaving the bottom block. This harvests connected blocks as well, only searching upwards.  

**crouch_bonemeal_chance**  
Chance (0.0-1.0) for the bonemeal effect to be applied to nearby crops and saplings when the player spams or holds crouch.  

**green_thumb**  
Chance (0.0-1.0) for crops planted by the player to become their Large variants upon reaching max growth state, or give double drops upon harvesting if a large variant is unavailable.  
Large variants can only appear when grown naturally (no bonemeal)  
Currently, only large carrots and beetroots are implemented.  

**zesty_culinary**  
If 1+, gain the Appetite MobEffect for 30 seconds upon crafting food.  
Cooking with the campfire will grant this effect for all players in a radius.  
The attribute also gives AoE when the player places a placeable food block (e.g.cake).  
The level of the effect will be equal to the attribute value's Integer, and the fraction(Decimal) will be used to determine the effect duration.  
The formula is as follows:  
Attribute value = `(effect level)` + `(durationSeconds/300-0.1)`  
ex. Attribute of `1.0` will give Appetite I for 30 seconds, `3.23` III for 99 seconds, `2.9` II for 5min.  
Basically, it gives base 30secs + additional 30 seconds per 0.1 attribute value.  

**short_order_cooking**  
Multiply cooking speed for Smokers and Campfires in a small radius around the player.  
e.g. 0 to completely stop progress, 2 to double cooking speed.  
Cooking with multiple players will multiply all of their values.  

**farmers_weapon**  
When using farmer's weapons (e.g. Hoe), the player gains extra damage.  

**farmers_armor**  
When using farmer's armors (e.g. Carved Pumpkin), the player gains extra armor.  

### MobEffects  
**Appetite**  
at level 1 : Allows the player to eat even when they're full  
at level 2+: when the player gains any other positive food effects, adds x0.5(configurable) of the original duration per (level-1)   

## Configuration
The following can be configured in the farmers_attributes-server.toml, unless otherwise specified.

**anti_farmland_trampling**  
Can be enabled for all players, ignoring their attribute values.  

**easy_harvest**  
Can be enabled for all players, ignoring their attribute values.  
Crops can be blacklisted.  
Whole-plant harvesting for kelp/sugarcane type plants can be disabled.  

**crouch_bonemeal_chance**  
The range and cooldown (in ticks, when holding crouch key) is configurable.  
The affected blocks are defined by the tag `#farmers_attributes:crouch_bonemeal_whitelist`.

**green_thumb**  
Affected crops and possible large variants can be blacklisted.  
Drop multiplier for non-large crops is configurable.  

**zesty_culinary**  
The duration of the Appetite given per level is configurable.  
The items that are not treated as "cooking" results (does not give effect) are defined by the tag: `#farmers_attributes:source_foods`  
The blocks that gives the effect when placed are defined by the tag: `#farmers_attributes:delicious_smelling_blocks`  

**short_order_cooking**  
The range to apply the speed modifier is configurable.  

**farmers_weapon**  
Farmer's weapons are defined by the tag: `#farmers_attributes:farmers_weapon`  

**farmers_armor**  
Farmer's weapons are defined by the tag: `#farmers_attributes:farmers_armor` 

**Appetite Effect**  
The multiplier for extended food effect duration is configurable.

## Integration
### Farmer's Delight
**zesty_culinary**  
Works with skillets and cooking pots.  
The skillet will apply the Appetite effect in an a radius like the campfire.  
The food blocks from Farmer's Delight (e.g. stuffed pumpkin) are included in the delicious_smelling_blocks tag by default.  

**short_order_cooking**  
Works on cooking pots and skillets.  
Values lower than 0.001 doesn't work on hand-held skillet cooking and will be handled as 0.001.

### Pufferfish's Skills
**easy_harvest**  
Harvested crops count as "broken" blocks in experience source contexts.  

## Installation
Needed on both server and client.

## Versions
The mod is available on neoforge 1.21.1, and will not be ported to other versions unless I personally need it be.

## Credits
Large crop textures by [@Siina__Makoto](https://x.com/Siina__Makoto)
