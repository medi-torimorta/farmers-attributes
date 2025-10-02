<img src="https://img.shields.io/curseforge/dt/1355713?style=flat&logo=CurseForge&logoColor=%23F16436&label=CurseForge&color=%23F16436&link=https%3A%2F%2Fwww.curseforge.com%2Fminecraft%2Fmc-mods%2Ffarmers-attributes" alt="CurseForge Downloads">  <img src="https://img.shields.io/badge/MC-1.21.1-green?style=flat&logo=minecraft&logoColor=white" alt="Minecraft Versions">  <img src="https://img.shields.io/badge/Side-Server%26Client-orange?style=flat" alt="Server Side">  <img src="https://img.shields.io/github/license/medi-torimorta/farmers-attributes?style=flat&color=purple" alt="License">
# Farmer's Attributes

## Overview
This mod adds multiple player attributes related to farming and cooking.

## Usage
It is recommended to use this with other mods that make use of the attributes, as the mod itself doesn't do anything other than adding the attributes to the game.

## Contents
### Attributes

**anti_farmland_trampling**  
If True, prevents farmlands from being trampled by the player.  

**crouch_bonemeal_chance**  
Chance for the bonemeal effect to be applied to nearby crops and saplings when the player spams or holds crouch.  
The range and cooldown (in ticks, when holding crouch key) is configurable in the config.
The affected blocks are defined by the tag `#farmers_attributes:crouch_bonemeal_whitelist`, and includes the following by default:  
`#minecraft:crops`  
`#minecraft:saplings`

**zesty_culinary**  
If 1+, gain the Appetite MobEffect for 30 seconds upon crafting any food.  
Cooking with the campfire will grant this effect for all players in range.
The level of the effect will be equal to the attribute value's Integer, and the fraction(Decimal) will be used to determine the effect duration.  
The formula is as follows:  
Attribute value = `(effect level)` + `(durationSeconds/300-0.1)`  
ex. Attribute of `1.0` will give Appetite I for 30 seconds, `3.23` III for 99 seconds, `2.9` II for 5min.  
Basically, it gives base 30secs + additional 30 seconds per 0.1 attribute value. 

### MobEffects  
**Appetite**  
at level 1 : Allows the player to eat even when they're full 
at level 2+: when the player gains any other positive food effects, adds x0.5(configurable) of the original duration per (level-1)   

## Integration
Farmer's delight compat is planned

## Installation
needed on both server and client

## Versions
The mod is available on neoforge 1.21.1, and will not be ported to other versions unless I personally need it be.
