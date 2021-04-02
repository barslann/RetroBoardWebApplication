package com.coderman.retroboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //-> auditing means tracking and logging every change or events related to entities, such as createdBy for the Comment entity
//this will enable auditing of the created data and created user of an entry in the table.!
//Auditing helps us in maintaining history records, which can later help us in tracking user activities. If implemented properly, auditing can also provide us similar functionality to version control systems.
public class RepoConfig {
}
