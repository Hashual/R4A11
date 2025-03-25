# Pierre-Papier-Ciseaux - Stratégies et Analyse

Ce projet implémente trois stratégies pour un jeu de Pierre-Papier-Ciseaux, chacune ayant une approche unique pour déterminer le prochain coup à jouer. Ce README présente une analyse de chaque stratégie, ses choix de conception et justifications, ainsi qu'une comparaison de leurs performances.

## Stratégies

### 1. **HumanStrategy**

#### Description :
La stratégie `HumanStrategy` cherche à simuler une prise de décision d'un joueur humain. Voici son fonctionnement :
- Si l'historique des coups est vide (au début du jeu), elle choisit un coup par défaut (dans ce cas, `Rock()`).
- Si les deux derniers coups de l'adversaire sont identiques, cette stratégie choisit de répéter le dernier coup de l'adversaire.
- Si les deux derniers coups de l'adversaire sont différents, elle choisit le contre-mouvement du dernier coup de l'adversaire, c'est-à-dire l'arme qui gagne contre ce dernier coup.

#### Justification :
- Cette stratégie imite un comportement humain qui peut être influencé par des modèles répétitifs ou des tentatives de "contre-attaque" après une série d'actions similaires.
- Le choix de répéter le coup précédent si l'adversaire a joué deux fois le même coup est basé sur l'idée qu'un humain pourrait simplement continuer avec le même coup en l'absence d'autres informations.
- L'option de contrer le dernier coup est une manière de punir les adversaires qui suivent une logique prévisible.

---

### 2. **ComputerStrategy**

#### Description :
La stratégie `ComputerStrategy` repose sur l'idée de repérer la tendance de l'adversaire et d'exploiter cette tendance en jouant un coup qui contre le plus fréquemment joué.
- Si un coup est joué plus fréquemment par l'adversaire, cette stratégie choisit le contre-coup de celui-ci (l'arme qui bat le coup le plus fréquent).
- Si aucun coup n'est clairement plus fréquent, la stratégie choisit un coup aléatoire parmi les trois armes disponibles.

#### Justification :
- Cette stratégie tente d'exploiter une éventuelle répétition des choix de l'adversaire, une tactique qui peut être efficace contre des joueurs prévisibles ou humains qui ont tendance à jouer certaines armes plus fréquemment.
- L'usage d'un coup aléatoire lorsqu'il n'y a pas de tendance claire rend cette stratégie imprévisible et plus difficile à contrer pour un joueur humain ou un autre programme.

---

### 3. **AdaptiveStrategy**

#### Description :
La stratégie `AdaptiveStrategy` essaie de s'adapter au comportement de l'adversaire en se basant sur l'historique récent de ses coups.
- Si les deux derniers coups de l'adversaire sont identiques, elle choisit de contrer ce dernier coup.
- Si les coups de l'adversaire sont différents, elle choisit un coup aléatoire parmi les trois disponibles.

#### Justification :
- Cette stratégie se concentre sur une réaction rapide aux comportements répétitifs de l'adversaire, ce qui peut être utile contre des stratégies comme celle du `HumanStrategy`, qui pourrait répéter son dernier coup.
- La sélection aléatoire lorsque les coups sont variés permet à la stratégie de rester imprévisible et non facilement contrable.

---

## Comparaison des Stratégies

Les stratégies sont comparées entre elles dans des simulations de 10 000 rounds de 1000 parties chacun. Voici les résultats des affrontements :

| Stratégie                                | Stratégie 1 | Stratégie 2 | Égalité |
|------------------------------------------|-------------|-------------|---------|
| **AdaptiveStrategy vs HumanStrategy**    | 304         | 405         | 291     |
| **AdaptiveStrategy vs ComputerStrategy** | 720         | 223         | 57      |
| **HumanStrategy vs ComputerStrategy**    | 250         | 250         | 500     |

**Note** : Ces valeurs sont la moyenne des résultats obtenus sur les 10 000 rounds. Un round correspond à une remise à zéro de l'historique des scores et des coups.

---

## Conclusion

Les différentes stratégies offrent des approches variées et intéressantes pour un jeu de Pierre-Papier-Ciseaux. Après avoir analysé les résultats des simulations, voici les principales observations :

- **La stratégie `HumanStrategy` l'emporte sur la `AdaptiveStrategy`**, mais la différence est assez serrée. Cela montre que bien que l'adaptive tente de réagir aux comportements de l'adversaire, la répétition et la contre-attaque typique d'une stratégie humaine semble souvent plus efficace.
- **La stratégie `AdaptiveStrategy` écrase la `ComputerStrategy`**, exploitant la prévisibilité de cette dernière pour obtenir de bons résultats.
- Enfin, **il y a une égalité parfaite entre la `HumanStrategy` et la `ComputerStrategy`**, ce qui suggère que les deux stratégies se neutralisent relativement bien, avec une chance similaire de gagner.

Les résultats des simulations montrent que la `HumanStrategy` est probablement la plus robuste contre des stratégies adaptatives, mais la `AdaptiveStrategy` offre un excellent moyen de contrer une stratégie trop rigide comme `ComputerStrategy`.
