CREATE TABLE `inventory` (
  `coord_x` bigint(20) unsigned NOT NULL,
  `coord_y` bigint(20) unsigned NOT NULL,
  `coord_z` bigint(20) unsigned NOT NULL,
  `goods_id` bigint(20) unsigned NOT NULL,
  `count` int(11) NOT NULL,
  `allocation_count` int(11) NOT NULL,
  `last_modification_time` timestamp(6) NOT NULL,
  PRIMARY KEY (`coord_x`,`coord_y`,`coord_z`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `inventory_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `operation_type` int(11) NOT NULL,
  `operation_id` bigint(20) DEFAULT NULL,
  `biz_id` bigint(20) NOT NULL,
  `coord_x` bigint(20) unsigned NOT NULL,
  `coord_y` bigint(20) unsigned NOT NULL,
  `coord_z` bigint(20) unsigned NOT NULL,
  `goods_id` bigint(20) unsigned NOT NULL,
  `count` int(11) NOT NULL,
  `allocation_count` int(11) NOT NULL,
  `change_count` int(11) NOT NULL,
  `creation_time` timestamp(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2854 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci