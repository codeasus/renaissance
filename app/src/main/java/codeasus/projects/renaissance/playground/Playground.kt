package codeasus.projects.renaissance.playground

import android.provider.ContactsContract
import java.util.*

const val PLAIN_FIRST_NAMES =
    "Marie Thomas Laura Nicolas Léa Julien Camille Maxime Chloé Quentin Manon Kevin Sarah Alexandre Emma Antoine Julie Alex Pauline Lucas Mathilde Clement Marine Romain Charlotte Pierre Lucie Florian Marion David Anaïs Guillaume Lisa Valentin Océane Paul Alice Jérémy Clara Hugo Justine Anthony Sophie Alexis Emilie Benjamin Morgane Theo Juliette Daniel Anna Adrien Mélanie Tom Louise Vincent Inès Mathieu Claire Simon Elodie Dylan Melissa Arthur Eva Nathan Margaux Jordan Amandine Louis Sara James Audrey Jonathan Elisa Leo Noémie Baptiste Julia Martin Caroline Axel Amélie Victor Clémence Corentin Jessica Jack Céline Thibault Maéva Samuel Emily Sebastien Jade Max Elise Marco Célia Adam Fanny Loïc Alexandra Robin Maria Ben Zoe Matthieu Margot Aurélien Aurélie William Estelle Gabriel Alicia Michael Léna Arnaud Romane Rémi Jeanne Damien Ophélie Raphael Hannah Enzo Andréa Ryan Olivia Bastien Lola Andrea Valentine Matteo Laurine Luca Victoria Tristan Nina Sam Laëtitia François Solène Marc Coralie Mickael Amy Dorian Megan Chris Aurore Charles Alexia Cédric Lauren Steven Lucy Liam Rébecca Benoit Cecile Mathis Marina Cyril Sandra Thibaut Agathe Christopher Emeline Maxence Laurie Florent Jennifer Jérôme Katie Gaëtan Ellie Francesco Elèna Christian Rachel Matthew Lou Fabien Elsa Yann Johanna John Sofia Adrian Chiara Tony Coline Mehdi Maëlle Jean Salomé Mohamed Carla Erwan"
const val PLAIN_LAST_NAMES =
    "Smith Johnson Williams Jones Brown Davis Miller Wilson Moore Taylor Anderson Thomas Jackson White Harris Martin Thompson Garcia Martinez Robinson Clark Rodriguez Lewis Lee Walker Hall Allen Young Hernandez King Wright Lopez Hill Scott Green Adams Baker Gonzalez Nelson Carter Mitchell Perez Roberts Turner Phillips Campbell Parker Evans Edwards Collins Stewart Sanchez Morris Rogers Reed Cook Morgan Bell Murphy Bailey Rivera Cooper Richardson Cox Howard Ward Torres Peterson Gray Ramirez James Watson Brooks Kelly Sanders Price Bennett Wood Barnes Ross Henderson Coleman Jenkins Perry Powell Long Patterson Hughes Flores Washington Butler Simmons Foster Gonzales Bryant Alexander Russell Griffin Diaz Hayes"

fun getFirstNames(): Set<String> {
    return PLAIN_FIRST_NAMES.split(" ").toSet()
}

fun getLastNames(): Set<String> {
    return PLAIN_LAST_NAMES.split(" ").toSet()
}

fun generateRandomPhoneNumbers(count: Int): Set<String> {
    val random = Random()
    val phoneNumbers = mutableSetOf<String>()

    for (i in 0..count) {
        val num1 = (random.nextInt(7) + 1) * 100 + random.nextInt(8) * 10 + random.nextInt(8)
        val num2 = random.nextInt(743)
        val num3 = random.nextInt(10000)
        phoneNumbers.add("+$num1$num2$num3")
    }

    return phoneNumbers
}

fun main() {
    val firstNames = getFirstNames().toMutableList().shuffled().take(100)
    var lastNames = getLastNames().toMutableList().shuffled().take(50)
    print(generateRandomPhoneNumbers(50))
}