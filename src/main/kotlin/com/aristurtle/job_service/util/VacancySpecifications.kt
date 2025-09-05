package com.aristurtle.job_service.util

import com.aristurtle.job_service.model.Vacancy
import org.springframework.data.jpa.domain.Specification

object VacancySpecifications {

    fun hasStatus(status: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<String>("status"), status)
        }
    }

    fun hasRegion(region: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<String>("region"), region)
        }
    }

    fun hasCity(city: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<String>("city"), city)
        }
    }

    fun containsAddress(address: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.like(builder.lower(root.get<String>("address")), "%${address.lowercase()}%")
        }
    }

    fun hasWorkType(workType: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<String>("workType"), workType)
        }
    }

    fun hasEmploymentType(employmentType: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<String>("employmentType"), employmentType)
        }
    }

    fun hasIncome(income: Int): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<Int>("income"), income)
        }
    }

    fun salaryGreaterThanOrEqual(salaryMin: Int): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.greaterThanOrEqualTo(root.get<Int>("salary"), salaryMin)
        }
    }

    fun salaryLessThanOrEqual(salaryMax: Int): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.lessThanOrEqualTo(root.get<Int>("salary"), salaryMax)
        }
    }

    fun hasWorkSchedule(workSchedule: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<String>("workSchedule"), workSchedule)
        }
    }

    fun hasAnnualBonus(annualBonus: Int): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<Int>("annualBonus"), annualBonus)
        }
    }

    fun hasBonusType(bonusType: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<String>("bonusType"), bonusType)
        }
    }

    fun hasEducationType(educationType: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<String>("educationType"), educationType)
        }
    }

    fun experienceGreaterThanOrEqual(experienceFrom: Int): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.greaterThanOrEqualTo(root.get<Int>("experienceYears"), experienceFrom)
        }
    }

    fun experienceLessThanOrEqual(experienceTo: Int): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.lessThanOrEqualTo(root.get<Int>("experienceYears"), experienceTo)
        }
    }

    fun hasBusinessTrips(businessTrips: Boolean): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.equal(root.get<Boolean>("businessTrips"), businessTrips)
        }
    }

    fun containsAdditionalInfo(additionalInfo: String): Specification<Vacancy> {
        return Specification { root, _, builder ->
            builder.like(builder.lower(root.get<String>("additionalInfo")), "%${additionalInfo.lowercase()}%")
        }
    }

    fun hasAnyResponsibility(responsibilities: List<String>): Specification<Vacancy> {
        return Specification { root, _, builder ->
            val responsibilityPredicates = responsibilities.map { responsibility ->
                builder.isMember(responsibility, root.get<List<String>>("responsibilities"))
            }
            builder.or(*responsibilityPredicates.toTypedArray())
        }
    }

    fun hasAnyRequirement(requirements: List<String>): Specification<Vacancy> {
        return Specification { root, _, builder ->
            val requirementPredicates = requirements.map { requirement ->
                builder.isMember(requirement, root.get<List<String>>("requirements"))
            }
            builder.or(*requirementPredicates.toTypedArray())
        }
    }

    fun hasAnyKnowledgeLanguage(languages: List<String>): Specification<Vacancy> {
        return Specification { root, _, builder ->
            val languagePredicates = languages.map { language ->
                builder.isMember(language, root.get<List<String>>("knowledgeLanguages"))
            }
            builder.or(*languagePredicates.toTypedArray())
        }
    }

    fun hasAnyLevelLanguage(levels: List<String>): Specification<Vacancy> {
        return Specification { root, _, builder ->
            val levelPredicates = levels.map { level ->
                builder.isMember(level, root.get<List<String>>("levelLanguages"))
            }
            builder.or(*levelPredicates.toTypedArray())
        }
    }

    fun hasAnyProgramRequirement(programs: List<String>): Specification<Vacancy> {
        return Specification { root, _, builder ->
            val programPredicates = programs.map { program ->
                builder.isMember(program, root.get<List<String>>("programRequirements"))
            }
            builder.or(*programPredicates.toTypedArray())
        }
    }
}