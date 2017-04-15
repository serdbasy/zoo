# zoo
Life style of animals where in zoo

ZOO:
You have a zoo in which there are some animals, and a zoo keeper who is responsible for the animals.
1. All animals have age, gender, id, location, weight in common.
  a. Ids should be unique for every animal type.
  b. All animals are placed randomly at the beginning of the game.
  c. All animals’ weight is 0 initially.
2. The animals are classified as domestic animals, and wild animals. 
  a. Domestic animals are sheep, dog, cat, chicken, fancy mouse.
  b. Wild animals are vole, fox, and crocodiles.
3. All animals get older during the game. They start at age 0 and may die (randomly) after some certain age.
  a. Wild animals: At every 6 cycle, they get one year older.
  b. Domestic animals: At every 7 cycle, they get one year older. 
  c. Vole, fancy mouse, chicken may die after age 400.
  d. Fox, crocodile may die after age 490. 
  e. Sheep, dog, cat may die after age 350.
4. Dead animals do not interact with the other animals, should be removed from zoo, and stored in a cemetery.
5. At every cycle, all animals randomly move (only if they have enough weight) with different speeds. If they move, they lose some weight:
  a. Chicken, sheep moves with speed of 2v, looses 1 weight. 
  b. Dog, cat, vole, fancy mouse, fox move with speed of 3v, looses 1.5 weight.
  c. Crocodiles move with speed of 4v, looses 2 weight.
  d. Animals shall not exceed the fences of the zoo.
6. At every cycle animals gain weight by the rules: 
  a. Domestic animals are fed by zoo keeper, and they gain weight by 0.7. 
  b. Sheep, chicken, fancy mouse, and vole are edible by the following rules:
    a) Vole: eats grass, can be ignored, at every cycle they gain weight by 0.5 
    b) Fox: eats chicken, fancy mouse, and vole. If eats, gain weight by 1
    c) Crocodile eats vole, chicken, fancy mouse, sheep, if eat gain weight by 2.
  c. Though being a domestic animal, cats also eat fancy mouse and vole gaining weight of 1.
  d. In order an animal to eat another one as listed above, they must come close as 5 points location. The eaten animals should be stored in cemetery.
7. If two animals of the same type (for instance two mice) but opposite gender (male and female) come close as 5 points, they mate. After they mate, a new offspring of the same type is produced and added to the game. The child is at age 0, of weight 0, gender and location is decided randomly, and acts normally.
8. The immoral zookeeper sometimes go hunting in the zoo. If he can shoot a chicken of weight greater than 10, he eats the chicken. Hunting happens randomly at every cycle.
