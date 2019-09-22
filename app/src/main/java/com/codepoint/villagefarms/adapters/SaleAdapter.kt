
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.models.Sale
import android.content.Intent
import com.codepoint.villagefarms.SalesDetailActivity





class SaleAdapter(private val saleList:ArrayList<Sale>):RecyclerView.Adapter<SaleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sales_individual_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return saleList.size
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtName?.text = saleList[position].firstname + " " + saleList[position].lastname
        holder.txtPhone?.text = saleList[position].phone


        // retrieve position of list item
        val items = saleList[position]

        // Event listener used to pass data for specific item to SalesDetail intent
        holder.itemView.setOnClickListener(View.OnClickListener { v ->


            val intent = Intent(v.context, SalesDetailActivity::class.java)
            intent.putExtra("objectId", items.objectId)
            intent.putExtra("firstName",items.firstname)
            intent.putExtra("lastName",items.lastname)
            v.context.startActivity(intent)

        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtName = itemView.findViewById<TextView>(R.id.list_fullname)
        val txtPhone = itemView.findViewById<TextView>(R.id.list_phone)

    }
}