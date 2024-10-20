package com.bintaaaa.storyappdicoding.utils

import com.bintaaaa.storyappdicoding.data.models.resposne.StoryItem

object DataDummy {
    fun generateDummyStoryData(): List<StoryItem>{
        val storyList = ArrayList<StoryItem>()
        for (i in 0..5){
            val story = StoryItem(
                photoUrl = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                createdAt = "2022-02-22T22:22:22Z",
                name = "Bopal $i",
                description = "Bopal anak ganteng  yang ke $i",
                lon = 106.8650,
                lat = -6.1745,
                id = "zzz-id-$i"
            )
            storyList.add(story)
        }
        return  storyList
    }
}

