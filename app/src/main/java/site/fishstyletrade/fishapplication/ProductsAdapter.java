package site.fishstyletrade.fishapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<Product> products;
    private static ClickListener clickListener;

    public ProductsAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.textView_id.setText(product.products_id.toString());
        holder.textView_name.setText(product.products_name);
        holder.textView_price.setText(product.products_price.toString());
        holder.textView_quantity.setText(product.products_quantity.toString());
        if (product.products_status == 0) {
            holder.textView_status.setText("отсутствует в продаже");
        } else {
            holder.textView_status.setText("в продаже");
        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textView_id, textView_name, textView_price, textView_quantity, textView_status;

        public ViewHolder(@NonNull View view) {
            super(view);
            textView_id = view.findViewById(R.id.textView_value_id);
            textView_name = view.findViewById(R.id.textView_value_name);
            textView_price = view.findViewById(R.id.textView_value_price);
            textView_quantity = view.findViewById(R.id.textView_value_quantity);
            textView_status = view.findViewById(R.id.textView_value_status);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ProductsAdapter.clickListener = clickListener;
    }
}
