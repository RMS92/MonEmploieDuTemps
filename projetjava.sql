-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  jeu. 04 juin 2020 à 15:18
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projetjava`
--

-- --------------------------------------------------------

--
-- Structure de la table `cours`
--

DROP TABLE IF EXISTS `cours`;
CREATE TABLE IF NOT EXISTS `cours` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `cours`
--

INSERT INTO `cours` (`id`, `nom`) VALUES
(1, 'Analyse de Fourier'),
(2, 'Thermodynamique'),
(3, 'POO Java'),
(4, 'Electronique'),
(5, 'Electromagnétique'),
(6, 'Amphi d\'informations'),
(7, 'Probabilités et statistiques'),
(8, 'Droit du travail'),
(9, 'Anglais'),
(10, 'Espagnol'),
(11, 'Language C'),
(12, 'Physique optique');

-- --------------------------------------------------------

--
-- Structure de la table `enseignant`
--

DROP TABLE IF EXISTS `enseignant`;
CREATE TABLE IF NOT EXISTS `enseignant` (
  `utilisateur_id` int(10) UNSIGNED NOT NULL,
  `cours_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`utilisateur_id`,`cours_id`),
  KEY `fk_cours` (`cours_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `enseignant`
--

INSERT INTO `enseignant` (`utilisateur_id`, `cours_id`) VALUES
(7, 1),
(11, 1),
(9, 2),
(2, 3),
(25, 3),
(8, 4),
(6, 5),
(2, 6),
(6, 6),
(11, 6),
(7, 7),
(11, 7),
(12, 8),
(13, 9),
(14, 10),
(2, 11),
(25, 11),
(24, 12);

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE IF NOT EXISTS `etudiant` (
  `utilisateur_id` int(10) UNSIGNED NOT NULL,
  `numero` int(10) UNSIGNED NOT NULL,
  `groupe_id` int(10) UNSIGNED NOT NULL,
  `promotion_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`utilisateur_id`),
  KEY `fk_groupe` (`groupe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`utilisateur_id`, `numero`, `groupe_id`, `promotion_id`) VALUES
(1, 100, 1, 1),
(5, 101, 1, 1),
(10, 102, 2, 1),
(15, 105, 2, 1),
(16, 106, 3, 2),
(17, 109, 3, 2),
(18, 107, 4, 2),
(19, 108, 4, 2),
(20, 111, 5, 3),
(21, 112, 5, 3),
(22, 115, 6, 3),
(23, 117, 6, 3);

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

DROP TABLE IF EXISTS `groupe`;
CREATE TABLE IF NOT EXISTS `groupe` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `promotion_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_promotion` (`promotion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `groupe`
--

INSERT INTO `groupe` (`id`, `nom`, `promotion_id`) VALUES
(1, 'Gr01', 1),
(2, 'Gr02', 1),
(3, 'Gr04', 2),
(4, 'Gr05', 2),
(5, 'Gr07', 3),
(6, 'Gr08', 3);

-- --------------------------------------------------------

--
-- Structure de la table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
CREATE TABLE IF NOT EXISTS `promotion` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `promotion`
--

INSERT INTO `promotion` (`id`, `nom`) VALUES
(1, 'Ing3'),
(2, 'Ing2'),
(3, 'Ing1');

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

DROP TABLE IF EXISTS `salle`;
CREATE TABLE IF NOT EXISTS `salle` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `capacite` int(10) UNSIGNED NOT NULL,
  `site_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `salle`
--

INSERT INTO `salle` (`id`, `nom`, `capacite`, `site_id`) VALUES
(1, 'P445', 60, 2),
(2, 'P315', 30, 3),
(3, 'P407', 30, 2),
(4, 'SC201', 30, 1),
(5, 'P415', 60, 2),
(6, 'P446', 60, 2),
(7, 'P405(labo)', 30, 2),
(8, 'EM009', 100, 1),
(9, 'EM010', 100, 1);

-- --------------------------------------------------------

--
-- Structure de la table `seance`
--

DROP TABLE IF EXISTS `seance`;
CREATE TABLE IF NOT EXISTS `seance` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `semaine` int(10) UNSIGNED NOT NULL,
  `date` varchar(50) NOT NULL,
  `heure_debut` int(10) NOT NULL,
  `heure_fin` varchar(50) NOT NULL,
  `etat` int(10) UNSIGNED NOT NULL,
  `cours_id` int(10) UNSIGNED NOT NULL,
  `type_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_coursSeance` (`cours_id`),
  KEY `fk_typeSeance` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `seance`
--

INSERT INTO `seance` (`id`, `semaine`, `date`, `heure_debut`, `heure_fin`, `etat`, `cours_id`, `type_id`) VALUES
(1, 1, '06/01/2020', 8, '9', 1, 7, 1),
(2, 1, '06/01/2020', 9, '10', 1, 3, 3),
(3, 1, '06/01/2020', 10, '11', 1, 5, 2),
(4, 1, '06/01/2020', 13, '14', 1, 4, 1),
(5, 1, '07/01/2020', 9, '10', 1, 7, 1),
(6, 1, '08/01/2020', 9, '10', 1, 7, 1),
(7, 1, '07/01/2020', 13, '14', 1, 3, 3),
(8, 1, '08/01/2020', 10, '11', 2, 5, 2),
(9, 1, '08/01/2020', 15, '16', 1, 2, 1),
(10, 1, '09/01/2020', 8, '9', 1, 3, 1),
(11, 1, '09/01/2020', 9, '10', 1, 3, 1),
(12, 1, '07/01/2020', 10, '11', 1, 6, 4),
(13, 1, '10/01/2020', 9, '10', 1, 1, 2),
(14, 1, '06/01/2020', 14, '15', 1, 4, 1),
(15, 1, '07/01/2020', 15, '16', 1, 7, 2),
(16, 1, '07/01/2020', 14, '15', 1, 3, 3),
(17, 1, '07/01/2020', 16, '17', 1, 8, 5),
(18, 1, '08/01/2020', 8, '9', 1, 9, 5),
(19, 1, '08/01/2020', 14, '15', 1, 4, 2),
(20, 1, '08/01/2020', 16, '17', 1, 5, 1),
(21, 1, '09/01/2020', 10, '11', 1, 10, 5),
(22, 1, '10/01/2020', 10, '11', 1, 1, 2),
(23, 1, '10/01/2020', 13, '14', 1, 1, 1),
(24, 1, '10/01/2020', 14, '15', 0, 3, 2),
(25, 1, '10/01/2020', 15, '16', 1, 2, 2),
(27, 1, '09/01/2020', 8, '9', 1, 10, 5),
(28, 2, '13/01/2020', 10, '11', 1, 5, 2),
(29, 2, '14/01/2020', 16, '17', 1, 7, 2),
(30, 2, '15/01/2020', 10, '11', 1, 5, 2),
(31, 2, '15/01/2020', 14, '15', 1, 4, 2),
(32, 2, '16/01/2020', 8, '9', 1, 3, 2),
(33, 2, '17/01/2020', 9, '10', 1, 1, 2),
(34, 2, '18/01/2020', 9, '10', 2, 1, 6),
(35, 2, '14/01/2020', 14, '15', 1, 7, 1),
(36, 2, '13/01/2020', 9, '10', 1, 4, 1),
(37, 2, '16/01/2020', 10, '11', 1, 10, 5),
(38, 1, '06/01/2020', 16, '17', 1, 7, 1),
(39, 1, '07/01/2020', 12, '13', 1, 1, 2),
(40, 1, '07/01/2020', 10, '11', 1, 7, 1),
(41, 1, '09/01/2020', 8, '9', 1, 11, 1),
(42, 1, '09/01/2020', 9, '10', 1, 11, 1),
(43, 1, '10/01/2020', 15, '16', 1, 12, 2),
(44, 1, '10/01/2020', 8, '9', 1, 11, 1),
(46, 1, '06/01/2020', 8, '9', 1, 1, 1),
(49, 1, '09/01/2020', 12, '13', 1, 1, 2),
(50, 1, '06/01/2020', 9, '10', 1, 4, 3),
(54, 1, '06/01/2020', 8, '9', 1, 6, 4),
(55, 1, '06/01/2020', 9, '10', 1, 5, 1),
(56, 1, '07/01/2020', 9, '10', 1, 11, 3),
(57, 1, '06/01/2020', 14, '15', 1, 3, 1),
(58, 1, '06/01/2020', 14, '15', 1, 1, 1),
(59, 1, '07/01/2020', 9, '10', 1, 7, 2),
(60, 2, '14/01/2020', 10, '11', 1, 11, 1),
(61, 2, '15/01/2020', 10, '11', 1, 1, 1),
(62, 2, '17/01/2020', 15, '16', 1, 12, 1);

-- --------------------------------------------------------

--
-- Structure de la table `seance_enseignant`
--

DROP TABLE IF EXISTS `seance_enseignant`;
CREATE TABLE IF NOT EXISTS `seance_enseignant` (
  `seance_id` int(10) UNSIGNED NOT NULL,
  `enseignant_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`seance_id`,`enseignant_id`),
  KEY `fk_enseignantEnseignant` (`enseignant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `seance_enseignant`
--

INSERT INTO `seance_enseignant` (`seance_id`, `enseignant_id`) VALUES
(7, 2),
(10, 2),
(11, 2),
(12, 2),
(16, 2),
(24, 2),
(32, 2),
(41, 2),
(54, 2),
(56, 2),
(3, 6),
(8, 6),
(12, 6),
(20, 6),
(28, 6),
(30, 6),
(54, 6),
(55, 6),
(1, 7),
(5, 7),
(6, 7),
(15, 7),
(29, 7),
(35, 7),
(38, 7),
(40, 7),
(58, 7),
(61, 7),
(4, 8),
(14, 8),
(19, 8),
(31, 8),
(36, 8),
(50, 8),
(9, 9),
(25, 9),
(12, 11),
(13, 11),
(22, 11),
(23, 11),
(33, 11),
(39, 11),
(46, 11),
(49, 11),
(54, 11),
(59, 11),
(17, 12),
(18, 13),
(21, 14),
(27, 14),
(37, 14),
(43, 24),
(62, 24),
(2, 25),
(42, 25),
(44, 25),
(57, 25),
(60, 25);

-- --------------------------------------------------------

--
-- Structure de la table `seance_etudiant`
--

DROP TABLE IF EXISTS `seance_etudiant`;
CREATE TABLE IF NOT EXISTS `seance_etudiant` (
  `seance_id` int(10) UNSIGNED NOT NULL,
  `utilisateur_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`seance_id`,`utilisateur_id`),
  KEY `fk_utilisateurEtudiant` (`utilisateur_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `seance_etudiant`
--

INSERT INTO `seance_etudiant` (`seance_id`, `utilisateur_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 1),
(19, 1),
(20, 1),
(21, 1),
(22, 1),
(23, 1),
(24, 1),
(25, 1),
(28, 1),
(29, 1),
(30, 1),
(31, 1),
(32, 1),
(33, 1),
(34, 1),
(35, 1),
(36, 1),
(37, 1),
(49, 1),
(1, 5),
(2, 5),
(3, 5),
(4, 5),
(5, 5),
(6, 5),
(7, 5),
(8, 5),
(9, 5),
(10, 5),
(11, 5),
(12, 5),
(13, 5),
(14, 5),
(15, 5),
(16, 5),
(17, 5),
(18, 5),
(19, 5),
(20, 5),
(21, 5),
(22, 5),
(23, 5),
(24, 5),
(25, 5),
(28, 5),
(29, 5),
(30, 5),
(31, 5),
(32, 5),
(33, 5),
(34, 5),
(35, 5),
(36, 5),
(37, 5),
(49, 5),
(3, 10),
(8, 10),
(12, 10),
(13, 10),
(15, 10),
(19, 10),
(22, 10),
(24, 10),
(25, 10),
(27, 10),
(28, 10),
(29, 10),
(30, 10),
(31, 10),
(32, 10),
(33, 10),
(34, 10),
(46, 10),
(49, 10),
(50, 10),
(3, 15),
(8, 15),
(12, 15),
(13, 15),
(15, 15),
(19, 15),
(22, 15),
(24, 15),
(25, 15),
(27, 15),
(28, 15),
(29, 15),
(30, 15),
(31, 15),
(32, 15),
(33, 15),
(34, 15),
(46, 15),
(49, 15),
(50, 15),
(38, 16),
(39, 16),
(43, 16),
(44, 16),
(38, 17),
(39, 17),
(43, 17),
(44, 17),
(39, 18),
(40, 18),
(41, 18),
(42, 18),
(43, 18),
(39, 19),
(40, 19),
(41, 19),
(42, 19),
(43, 19),
(54, 20),
(55, 20),
(59, 20),
(62, 20),
(54, 21),
(55, 21),
(59, 21),
(62, 21),
(54, 22),
(56, 22),
(57, 22),
(58, 22),
(59, 22),
(60, 22),
(61, 22),
(54, 23),
(56, 23),
(57, 23),
(58, 23),
(59, 23),
(60, 23),
(61, 23);

-- --------------------------------------------------------

--
-- Structure de la table `seance_groupe`
--

DROP TABLE IF EXISTS `seance_groupe`;
CREATE TABLE IF NOT EXISTS `seance_groupe` (
  `seance_id` int(10) UNSIGNED NOT NULL,
  `groupe_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`seance_id`,`groupe_id`),
  KEY `fk_groupeSeance` (`groupe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `seance_groupe`
--

INSERT INTO `seance_groupe` (`seance_id`, `groupe_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 1),
(19, 1),
(20, 1),
(21, 1),
(22, 1),
(23, 1),
(24, 1),
(25, 1),
(28, 1),
(29, 1),
(30, 1),
(31, 1),
(32, 1),
(33, 1),
(34, 1),
(35, 1),
(36, 1),
(37, 1),
(49, 1),
(3, 2),
(8, 2),
(12, 2),
(13, 2),
(15, 2),
(19, 2),
(22, 2),
(24, 2),
(25, 2),
(27, 2),
(28, 2),
(29, 2),
(30, 2),
(31, 2),
(32, 2),
(33, 2),
(34, 2),
(46, 2),
(49, 2),
(50, 2),
(38, 3),
(39, 3),
(43, 3),
(44, 3),
(39, 4),
(40, 4),
(41, 4),
(42, 4),
(43, 4),
(54, 5),
(55, 5),
(59, 5),
(62, 5),
(54, 6),
(56, 6),
(57, 6),
(58, 6),
(59, 6),
(60, 6),
(61, 6);

-- --------------------------------------------------------

--
-- Structure de la table `seance_salle`
--

DROP TABLE IF EXISTS `seance_salle`;
CREATE TABLE IF NOT EXISTS `seance_salle` (
  `seance_id` int(10) UNSIGNED NOT NULL,
  `salle_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`seance_id`,`salle_id`),
  KEY `fk_salleSeance` (`salle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `seance_salle`
--

INSERT INTO `seance_salle` (`seance_id`, `salle_id`) VALUES
(3, 1),
(8, 1),
(15, 1),
(28, 1),
(29, 1),
(35, 1),
(39, 1),
(43, 1),
(49, 1),
(62, 1),
(18, 2),
(21, 2),
(27, 2),
(37, 2),
(42, 2),
(46, 2),
(50, 2),
(56, 2),
(57, 2),
(60, 2),
(1, 3),
(5, 3),
(6, 3),
(9, 3),
(17, 3),
(23, 3),
(38, 3),
(40, 3),
(41, 3),
(55, 3),
(58, 3),
(61, 3),
(2, 4),
(7, 4),
(10, 4),
(11, 4),
(16, 4),
(20, 4),
(44, 4),
(25, 5),
(13, 6),
(22, 6),
(30, 6),
(32, 6),
(33, 6),
(4, 7),
(14, 7),
(36, 7),
(12, 8),
(19, 8),
(24, 8),
(31, 8),
(34, 8),
(54, 8),
(59, 8),
(12, 9),
(34, 9),
(54, 9);

-- --------------------------------------------------------

--
-- Structure de la table `site`
--

DROP TABLE IF EXISTS `site`;
CREATE TABLE IF NOT EXISTS `site` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `site`
--

INSERT INTO `site` (`id`, `nom`) VALUES
(1, 'Eiffel 1'),
(2, 'Eiffel 2'),
(3, 'Eiffel 3'),
(4, 'Eiffel 4'),
(5, 'Eiffel 5');

-- --------------------------------------------------------

--
-- Structure de la table `type_cours`
--

DROP TABLE IF EXISTS `type_cours`;
CREATE TABLE IF NOT EXISTS `type_cours` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `type_cours`
--

INSERT INTO `type_cours` (`id`, `name`) VALUES
(1, 'TD'),
(2, 'Cours magistral'),
(3, 'TP'),
(4, 'Amphi infos'),
(5, 'Cours'),
(6, 'Examen');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `prenom` varchar(25) NOT NULL,
  `droit` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `password`, `nom`, `prenom`, `droit`) VALUES
(1, 'romain.bernard@edu.ece.fr', '1234', 'Bernard', 'Romain', 4),
(2, 'jp.segado@edu.ece.fr', '1234', 'Segado', 'Jean-pierre', 3),
(3, 'admin@edu.ece.fr', 'admin', 'Admin', 'Admin', 1),
(4, 'referent@edu.ece.fr', '1234', 'Referent', 'Pedagogique', 2),
(5, 'nicolas.lambert@gmail.com', '1234', 'Lambert', 'Nicolas', 4),
(6, 'christine.crambes@gmail.com', '1234', 'Crambes', 'Christine', 3),
(7, 'cerbah@edu.ece.fr', '1234', 'Cerbah', 'Said', 3),
(8, 'lopes.nicolas@edu.ece.fr', '1234', 'Lopes', 'Nicolas', 3),
(9, 'thomas.guillemot@edu.ece.fr', '1234', 'Guillemot', 'Thomas', 3),
(10, 'emilien.delahegue@edu.ece.fr', '1234', 'Delahegue', 'Emilien', 4),
(11, 'fabienne.coudray@edu.ece.fr', '1234', 'Coudray', 'Fabienne', 3),
(12, 'maupile.yves@edu.ece.fr', '1234', 'Maupile', 'Yves', 3),
(13, 'reese.james@edu.ece.fr', '1234', 'Reese', 'James', 3),
(14, 'murillo.brigida@edu.ece.fr', '1234', 'Murillo', 'Brigida', 3),
(15, 'gautier.plante@edu.ece.fr', '1234', 'Plante', 'Gautier', 4),
(16, 'pierre.mijon@edu.ece.fr', '1234', 'Mijon', 'Pierre', 4),
(17, 'lucie.blin@edu.ece.fr', '1234', 'Blin', 'Lucie', 4),
(18, 'louis.thivant@edu.ece.fr', '1234', 'Thivant', 'Louis', 4),
(19, 'gabriel.attal@edu.ece.fr', '1234', 'Attal', 'Gabriel', 4),
(20, 'leonard.najman@edu.ece.fr', '1234', 'Najman', 'Leonard', 4),
(21, 'justine.reynaud@edu.ece.fr', '1234', 'Reynaud', 'Justine', 4),
(22, 'paul.coutiere@edu.ece.fr', '1234', 'Coutiere', 'Paul', 4),
(23, 'hippolyte.taliercio@edu.ece.fr', '1234', 'Taliercio', 'Hippolyte', 4),
(24, 'muller.françois@edu.ece.fr', '1234', 'Muller', 'François', 3),
(25, 'rendler.elisabeth@edu.ece.fr', '1234', 'Rendler', 'Elisabeth', 3);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `enseignant`
--
ALTER TABLE `enseignant`
  ADD CONSTRAINT `fk_cours` FOREIGN KEY (`cours_id`) REFERENCES `cours` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_utilisateurEnseignant` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD CONSTRAINT `fk_groupe` FOREIGN KEY (`groupe_id`) REFERENCES `groupe` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_utilisateur` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `groupe`
--
ALTER TABLE `groupe`
  ADD CONSTRAINT `fk_promotion` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `salle`
--
ALTER TABLE `salle`
  ADD CONSTRAINT `fk_site` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `seance`
--
ALTER TABLE `seance`
  ADD CONSTRAINT `fk_coursSeance` FOREIGN KEY (`cours_id`) REFERENCES `cours` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_typeSeance` FOREIGN KEY (`type_id`) REFERENCES `type_cours` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `seance_enseignant`
--
ALTER TABLE `seance_enseignant`
  ADD CONSTRAINT `fk_enseignantEnseignant` FOREIGN KEY (`enseignant_id`) REFERENCES `enseignant` (`utilisateur_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_seanceEnseignant` FOREIGN KEY (`seance_id`) REFERENCES `seance` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `seance_etudiant`
--
ALTER TABLE `seance_etudiant`
  ADD CONSTRAINT `fk_seanceEtudiant` FOREIGN KEY (`seance_id`) REFERENCES `seance` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_utilisateurEtudiant` FOREIGN KEY (`utilisateur_id`) REFERENCES `etudiant` (`utilisateur_id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `seance_groupe`
--
ALTER TABLE `seance_groupe`
  ADD CONSTRAINT `fk_groupeSeance` FOREIGN KEY (`groupe_id`) REFERENCES `groupe` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_seance` FOREIGN KEY (`seance_id`) REFERENCES `seance` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `seance_salle`
--
ALTER TABLE `seance_salle`
  ADD CONSTRAINT `fk_salleSeance` FOREIGN KEY (`salle_id`) REFERENCES `salle` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_seanceSalle` FOREIGN KEY (`seance_id`) REFERENCES `seance` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
