package com.manageairproducts.petinfomate.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName


data class Animal(
    val name: String,
    val locations: List<String>,
    val characteristics: Characteristics
){
    fun toEntity(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}


data class Characteristics(
    val prey: String?,
    @SerializedName("name_of_young") val nameOfYoung: String?,
    @SerializedName("group_behavior") val groupBehavior: String?,
    @SerializedName("biggest_threat") val biggestThreat: String?,
    @SerializedName("most_distinctive_feature") val mostDistinctiveFeature: String?,
    @SerializedName("gestation_period") val gestationPeriod: String?,
    @SerializedName("litter_size") val litterSize: String?,
    val habitat: String?,
    val diet: String?,
    val type: String?,
    @SerializedName("common_name") val commonName: String?,
    val origin: String?,
    @SerializedName("number_of_species") val numberOfSpecies: String?,
    val location: String?,
    val color: String?,
    @SerializedName("skin_type") val skinType: String?,
    @SerializedName("top_speed") val topSpeed: String?,
    val lifespan: String?,
    val weight: String?,
    val height: String?,
    val length: String?,
    @SerializedName("age_of_sexual_maturity") val ageOfSexualMaturity: String?,
    @SerializedName("age_of_weaning") val ageOfWeaning: String?,
    @SerializedName("estimated_population_size") val estimatedPopulationSize: String?,
    @SerializedName("other_name") val otherName: String?,
    val slogan: String?,
    val lifestyle: String?,
    @SerializedName("main_prey") val mainPrey: String?,
    @SerializedName("average_litter_size") val averageLitterSize: String?,
    val predators: String?,
    @SerializedName("favorite_food") val favoriteFood: String?,
    @SerializedName("water_type") val waterType: String?,
    @SerializedName("optimum_ph_level") val optimumPhLevel: String?,
    @SerializedName("average_clutch_size") val averageClutchSize: String?,
    @SerializedName("distinctive_feature") val distinctiveFeature: String?,
    val group: String?
)


