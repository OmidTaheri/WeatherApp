package ir.omidtaheri.mainpage.ui.MainFragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.omidtaheri.androidbase.BaseViewHolder
import ir.omidtaheri.mainpage.databinding.LocationListEmptyStateBinding
import ir.omidtaheri.mainpage.databinding.LocationListItemBinding
import ir.omidtaheri.mainpage.entity.LocationEntity.LocationUiEntity


class SearchLocationAdapter(val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    var items: MutableList<LocationUiEntity> = mutableListOf()

    val VIEW_TYPE_EMPTY = 0
    val VIEW_TYPE_NORMAL = 1

    lateinit var mCallback: Callback

    interface Callback {
        fun onLocationItemClick(locationUiEntity: LocationUiEntity)
    }

    fun setCallback(callback: Callback) {
        mCallback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        when (viewType) {

            VIEW_TYPE_EMPTY -> {
                return EmptyViewHolder(
                    LocationListEmptyStateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else ->
                return ViewHolder(
                    LocationListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }

    override fun getItemCount(): Int {
        return if (items.size != 0) {
            items.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    //    Helpers
    fun addItem(item: LocationUiEntity) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(list: List<LocationUiEntity>) {
        clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun remove(item: LocationUiEntity) {
        val index = items.indexOf(item)
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: LocationListItemBinding) : BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val locationEntity = items.get(position)

            binding.apply {

                locationFullName.text = locationEntity.fullName
                locationShortName.text = locationEntity.shortName

                root.setOnClickListener {
                    mCallback.onLocationItemClick(locationEntity)
                }
            }
        }
    }

    inner class EmptyViewHolder(val binding: LocationListEmptyStateBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {

            binding.apply {
            }
        }
    }


}
