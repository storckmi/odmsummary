--
-- Datenbank: `odmsummary`
--
CREATE DATABASE IF NOT EXISTS `odmsummary` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `odmsummary`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sequence`
--

CREATE TABLE IF NOT EXISTS `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sequence` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', '1');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `umlscode`
--

CREATE TABLE IF NOT EXISTS `umlscode` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE USER 'odmsummary'@'localhost' IDENTIFIED BY 'odmsummary';
GRANT SELECT , INSERT , UPDATE , DELETE , CREATE , DROP , INDEX , ALTER ON `odmsummary` . * TO 'odmsummary'@'localhost';