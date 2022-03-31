package com.example.melitest.results.viewmodel

import com.example.melitest.api.ItemAttributes
import com.example.melitest.api.MeliItem
import com.example.melitest.api.MeliPaging
import com.example.melitest.api.MeliResponse

object DummyMeliResponse {

    fun getDummyMeliEmptyResponse() = MeliResponse(getDummyMeliEmptyPaging(),
        getDummyMeliItemEmptyList())

    private fun getDummyMeliEmptyPaging() = MeliPaging(0)

    private fun getDummyMeliItemEmptyList() = listOf<MeliItem>()

    fun getDummyMeliResponse() = MeliResponse(getDummyMeliPaging(), getDummyMeliItemList())

    private fun getDummyMeliPaging() = MeliPaging(1000)

    private fun getDummyMeliItemList() = listOf<MeliItem>(
        MeliItem("title1",100.00,"USD","new 1","thumbnail1",
        getDummyMeliAttributes()),
        MeliItem("title2",100.00,"USD","new 2","thumbnail2",
            getDummyMeliAttributes()),
        MeliItem("title3",100.00,"USD","new 3","thumbnail3",
            getDummyMeliAttributes())
    )

    private fun getDummyMeliAttributes() = listOf<ItemAttributes>(
        ItemAttributes("Name1","valueName1"),
        ItemAttributes("Name2","valueName2"),
        ItemAttributes("Name2","valueName2")
    )


}