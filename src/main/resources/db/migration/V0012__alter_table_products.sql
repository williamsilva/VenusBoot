ALTER TABLE `venus`.`products` 
CHANGE COLUMN `valid` `validFinal` DATE NULL ;

ALTER TABLE `venus`.`products` 
ADD COLUMN `validInicial` DATE NULL AFTER `value`;