package com.chocolang.android.chocoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chocolang.android.chocoapp.databinding.ActivityMainBinding
import com.chocolang.android.chocoapp.repository.ui.fragment.EmailFragment
import com.chocolang.android.chocoapp.repository.ui.fragment.ListFragment
import com.chocolang.android.chocoapp.repository.ui.fragment.MapFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    enum class FragmentTagType(val title: String, val type: Int) {
        EMAIL("EMAIL", 0),
        INFO("INFO", 1),
        MAP("MAP", 2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, getFragment(FragmentTagType.EMAIL))
            .commit()

        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.tab1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container_view,
                                getFragment(FragmentTagType.EMAIL),
                            ).commit()
                        true
                    }
                    R.id.tab2 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container_view,
                                getFragment(FragmentTagType.INFO),
                            ).commit()
                        true
                    }
                    R.id.tab3 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container_view,
                                getFragment(FragmentTagType.MAP),
                            ).commit()
                        true
                    }
                    else -> throw Exception("unknown fragment id")
                }
            }
        }
    }

    private fun getFragment(tag: FragmentTagType): Fragment {
        val fragment = supportFragmentManager.findFragmentByTag(tag.title)
        if (fragment == null) {
            return when (tag) {
                FragmentTagType.EMAIL -> EmailFragment()
                FragmentTagType.INFO -> ListFragment.newInstance("10", "20")
                FragmentTagType.MAP -> MapFragment()
            }
        } else {
            return fragment
        }
    }
}