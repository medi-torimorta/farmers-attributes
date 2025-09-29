# Farmer's Attributes

## Overview
This mod adds multiple player attributes related to farming and cooking.

## Usage
It is recommended to use this with other mods that make use of the attributes, as the mod itself doesn't do anything other than adding the attributes to the game.

## Contents
The mod currently implements the following attributes:

**anti_farmland_trampling**  
If True, prevents farmlands from being trampled by the player.  

**crouch_bonemeal_chance**  
Chance (by percentage) for the bonemeal effect to be applied to nearby crops and saplings when the player spams or holds crouch.  
The range and cooldown (in ticks, when holding crouch key) is configurable in the config.
The affected blocks are defined by the tag `#farmingattributes:crouch_bonemeal_whitelist`, and includes the following by default:  
`#minecraft:crops`  
`#minecraft:saplings`

## Integration
Farmer's delight compat is planned

## Installation
needed on both server and client

## Versions
The mod is available on neoforge 1.21.1, and will not be ported to other versions unless I personally need it be.
