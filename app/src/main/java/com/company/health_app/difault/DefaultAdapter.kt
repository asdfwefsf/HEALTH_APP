package com.company.health_app.difault

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class DefaultAdapter(@Suppress("unused")private val defaultActivity: DefaultActivity) : FragmentStateAdapter(defaultActivity) {


    override fun getItemCount(): Int {
        return 5
    }


    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return DefaultFragment.newInstance(
                    position,
                    "벤치프레스  ", "인클라인벤치 ", "디클라인벤치 ", "체스트프레스 ",
                    "체스트플라이 ", "푸쉬업    ", "풀오버    "
                )
            }
            1 -> {
                return DefaultFragment.newInstance(
                    position,
                    "사레레    ", "프론트레이즈 ", "업라이트로우 ", "케이블리버스플라이", "밀리터리프레스",
                    "아놀드프레스", "덤벨숄더프레스"
                )
            }
            2 -> {
                return DefaultFragment.newInstance(
                    position,
                    "데드리프트  ", "루마니안데드 ", "랫풀다운   ", "시티드로우  ",
                    "원암로우   ", "턱걸이    ", "바벨로우   "
                )
            }
            3 -> {
                return DefaultFragment.newInstance(
                    position,
                    "바벨컬    ", "덤벨컬    ", "해머컬    ", "케이블푸쉬다운", "딥스     ",
                    "덤벨익스텐션 ", "클로즈벤치프레스"
                )
            }
            4 -> {
                return DefaultFragment.newInstance(
                    position,
                    "스쿼트    ", "레그익스텐션 ", "파워레그프레스", "레그프레스  ",
                    "레그컬    ", "핵스쿼트   ", "힙어브덕션  "
                )
            }
            else -> return DefaultFragment.newInstance(position, "", "", "", "", "", "", "")
        }
    }
}
