import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lxy.songsearch.databinding.ItemSongsBinding
import com.lxy.songsearch.pojo.SongInfo


class SongsListAdapter :
    RecyclerView.Adapter<SongsListAdapter.SongsListViewHolder>() {
    private val items: ArrayList<SongInfo> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        val binding = ItemSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    fun setNewInstance(songs: List<SongInfo>) {
        items.clear()
        items.addAll(songs)
        notifyDataSetChanged()
    }


    inner class SongsListViewHolder(private val binding: ItemSongsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SongInfo) {
            binding.run {
                tvTrackName.text = item.trackName
                tvArtistName.text = item.artistName
                tvPrice.text = "Price:$${item.trackPrice}"
            }


        }
    }


}
