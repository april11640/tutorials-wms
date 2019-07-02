CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `inventory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coord_x` bigint(20) unsigned NOT NULL,
  `coord_y` bigint(20) unsigned NOT NULL,
  `coord_z` bigint(20) unsigned NOT NULL,
  `goods_id` bigint(20) unsigned NOT NULL,
  `count` int(11) NOT NULL,
  `allocation_count` int(11) NOT NULL,
  `last_modification_time` timestamp(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_inventory` (`coord_x`,`coord_y`,`coord_z`,`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `purchase_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `creation_time` timestamp(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `purchase_order_goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL,
  `goods_id` bigint(20) unsigned NOT NULL,
  `count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `purchase_order_allocation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL,
  `coord_x` bigint(20) unsigned NOT NULL,
  `coord_y` bigint(20) unsigned NOT NULL,
  `coord_z` bigint(20) unsigned NOT NULL,
  `goods_id` bigint(20) unsigned NOT NULL,
  `count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;