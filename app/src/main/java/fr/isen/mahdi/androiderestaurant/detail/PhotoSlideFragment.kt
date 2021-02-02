import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.mahdi.androiderestaurant.databinding.SliderItemBinding

class PhotoSlideFragment(private val image: String?) : Fragment() {
    private lateinit var binding: SliderItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SliderItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (image != null) {
            if (image.isNotEmpty()) {
                Picasso.get().load(image).into(binding.dishImageSlide)
            }
        }
    }
}