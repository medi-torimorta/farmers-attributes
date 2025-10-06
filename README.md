# Farmer's Attributes
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/1355713?style=flat&logo=CurseForge&logoColor=F16436&label=CurseForge&color=F16436)](https://www.curseforge.com/minecraft/mc-mods/farmers-attributes)
[![Minecraft Versions](https://img.shields.io/badge/MC-1.21.1-green?style=flat&logo=minecraft&logoColor=white)](https://www.minecraft.net/ja-jp/about-minecraft)
[![Server Side](https://img.shields.io/badge/Side-Server%26Client-orange?style=flat)](#)
[![License](https://img.shields.io/github/license/medi-torimorta/farmers-attributes?style=flat&color=purple)](https://github.com/medi-torimorta/farmers-attributes/?tab=MIT-1-ov-file#)

This mod aims to enhance farming experience in minecraft by adding multiple player attributes related to farming and cooking.

## Usage
It is recommended to use this with other mods that make use of the attributes, as the mod itself doesn't do anything other than adding the attributes to the game.  
You can also use the commands to set your attributes.  
ex.  
`/attribute @s farmers_attributes:crouch_bonemeal_chance base set 0.5`

## Contents
### Attributes

**anti_farmland_trampling**  
If True (non-zero), prevents farmlands from being trampled by the player.  

**crouch_bonemeal_chance**  
Chance (0.0-1.0) for the bonemeal effect to be applied to nearby crops and saplings when the player spams or holds crouch.  

**zesty_culinary**  
If 1+, gain the Appetite MobEffect for 30 seconds upon crafting food.  
Cooking with the campfire will grant this effect for all players in a radius.  
The attribute also gives AoE when the player places a placeable food block (e.g.cake).  
The level of the effect will be equal to the attribute value's Integer, and the fraction(Decimal) will be used to determine the effect duration.  
The formula is as follows:  
Attribute value = `(effect level)` + `(durationSeconds/300-0.1)`  
ex. Attribute of `1.0` will give Appetite I for 30 seconds, `3.23` III for 99 seconds, `2.9` II for 5min.  
Basically, it gives base 30secs + additional 30 seconds per 0.1 attribute value.  

**green_thumb**
Chance (0.0-1.0) for crops planted by the player to become their Large variants upon reaching max growth state, or give double drops upon harvesting. The large variants can only appear when grown naturally (e.g. no bonemeal)

### MobEffects  
**Appetite**  
at level 1 : Allows the player to eat even when they're full  
at level 2+: when the player gains any other positive food effects, adds x0.5(configurable) of the original duration per (level-1)   

## Configuration
**crouch_bonemeal_chance**  
The range and cooldown (in ticks, when holding crouch key) is configurable in the configs.  
The affected blocks are defined by the tag `#farmers_attributes:crouch_bonemeal_whitelist`.

**zesty_culinary**  
The duration of the Appetite given per level is configurable in the configs.  
The items that are not treated as "cooking" results (does not give effect) are defined by the tag: `#farmers_attributes:source_foods`  
The blocks that gives the effect when placed are defined by the tag: `#farmers_attributes:delicious_smelling_blocks`  

## Integration
### Farmer's Delight
**zesty_culinary**  
Works with skillets and cooking pots.  
The skillet will apply the Appetite effect in an a radius like the campfire.  
The food blocks from Farmer's Delight (e.g. stuffed pumpkin) are included in the delicious_smelling_blocks tag by default.  

## Installation
Needed on both server and client.

## Versions
The mod is available on neoforge 1.21.1, and will not be ported to other versions unless I personally need it be.

## Credits
Large crop textures by [@Siina__Makoto](https://x.com/Siina__Makoto)