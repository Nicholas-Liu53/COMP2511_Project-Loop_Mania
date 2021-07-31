## Assumptions ##

## Character
- The main character starts with 100 health points
- The character initially starts with 0 gold, 0 equipment and can only attack with their hands
- Character class stores health, gold  and attack damage
- Character’s inventory is stored in loopmaniaworld

## Experience XP
- Character gets a random amount of XP between 50-500 for each battle won

## Melee
- Initial Damage = 5

## Shop
- Cannot sell equipped items

## Items
- Selling Price = 80% x Purchase Price
- All items are non-perishable (last forever until replaced by new item)
- Defense points reduce enemy’s attack damage by certain %

## Sword
- Purchase = 200
- Selling = 160
- Max battles = 5
- Increase damage by 10 points

## Stake 
- Purchase = 150
- Selling = 120
- Max battles = 3
- Increase damage by 5 points
- Vampire critical was 15 points

## Staff
- Purchase = 300
- Selling = 240
- Max battle = 10
- Increase damage by 3 points
- When used in battle, on each attack the staff should have a ¼ chance of inflicting a trance, which transforms an attacked enemy into an allied soldier for 5 seconds

## Body Armour
- Reduces enemy attacks by 50%
- Purchase = 500
- Selling = 400 

## Shield
- Reduces enemy attacks by 30%
- Purchase = 300
- Selling = 240 

## Helmet
- Reduces enemy attacks by 20%
- Purchase = 200
- Selling = 160

## Gold
- Character gets random amount of gold 50-500 after defeating enemies
- Character starts with 0 amount 

## Potion
- Use key ‘P’ to use potion
- Restores all health points of character
- Purchase = 125 gold
- Sell = 100 gold
- One Ring
- Activates on its own when character dies

## Enemies
- All enemies, move randomly along the path unless specific behavior conditions (e.g. vampire runs away from campfires)
- All enemy attacks are in damagePoints/sec
- No limits for number of enemies

## Slug
- Health point = 25
- Damage given = 3
- Defence = 0% (reduces Character/Allies attack by certain percentage)
- Speed = 1 attack per tick
- Battle Radius = Support Radius = 1 tile length

## Vampire
- Health point = 75
- Damage given by vampire = 15
- Defence = 20% (reduce Character’s attack damage 20%)
- Speed = 1 attack per third tick
- Battle Radius = 2 tile length
- Support Radius = 3 tile length

## Zombie
- Health point = 50
- Damage = 10
- Defence = 5
- Speed = 1 attack per tick
- Battle Radius = 2 tile length
- Support Radius = 2 tile length

## Buildings
## Vampire Castle Building
- For every 5 cycles of the path completed by the Character, an extra vampire spawns
- The number of vampires spawned by the Vampire Castle = number of cycles completed by character % 5

## Zombie Pit Building
- For every 2 cycles of the path completed by the Character, an extra vampire spawns

## Tower
- Shooting radius = 2
- Damage = 10 every 3 seconds

## Village 
- Full health regeneration

## Barracks
- For every 5 cycles of the path completed by the Character, an extra soldier spawns

## Soldier
- Health point = 10
- Damage = 5

## Trap 
- Damage = 30

## Campfire Building
- Radius = 4 tiles

# Graphics Sources

Lunar Lander:
https://opengameart.org/content/apollo-moon-landing-sprites

Helmet:
https://www.reddit.com/r/PixelArt/comments/hlpdvs/astronaut_helmet_by_me/

Astronaut:
http://pixelartmaker.com/art/0b861aa4c9f88f9

Menu Background
https://i.pinimg.com/originals/86/b9/32/86b932f7e7a14702128570465d5e897e.png

