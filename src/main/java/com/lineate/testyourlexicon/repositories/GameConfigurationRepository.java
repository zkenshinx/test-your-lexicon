package com.lineate.testyourlexicon.repositories;

import com.lineate.testyourlexicon.entities.GameConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameConfigurationRepository extends JpaRepository<GameConfiguration, Long> {
}
