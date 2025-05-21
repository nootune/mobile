package ru.mirea.khvatovkl.employeedb;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // --- Entity ---
    @Entity(tableName = "superhero")
    public static class Superhero {
        @PrimaryKey(autoGenerate = true)
        public long id;
        public String name;
        public String superpower;
        public String universe;
    }
    // --- DAO ---
    @Dao
    public interface SuperheroDao {
        @Query("SELECT * FROM superhero")
        List<Superhero> getAll();
        @Query("SELECT * FROM superhero WHERE id = :id")
        Superhero getById(long id);
        @Insert
        void insert(Superhero superhero);
        @Update
        void update(Superhero superhero);
        @Delete
        void delete(Superhero superhero);
    }
    // --- Database ---
    @Database(entities = {Superhero.class}, version = 1)
    public abstract static class AppDatabase extends RoomDatabase {
        public abstract SuperheroDao superheroDao();
    }
    private AppDatabase db;
    private SuperheroDao dao;
    private EditText EditTextName, EditTextSuperpower, EditTextUniverse;
    private Button buttonAdd, buttonUpdate, buttonDelete;
    private RecyclerView recycleView;
    private SuperheroAdapter adapter;
    private long selectedHeroId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ваш layout

        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "superheroes_db")
                .allowMainThreadQueries()
                .build();
        dao = db.superheroDao();

        EditTextName = findViewById(R.id.EditTextname);
        EditTextSuperpower = findViewById(R.id.EditTextsuperpower);
        EditTextUniverse = findViewById(R.id.EditTextuniverse);
        buttonAdd = findViewById(R.id.buttonadd);
        buttonUpdate = findViewById(R.id.buttonupdate);
        buttonDelete = findViewById(R.id.buttondelete);
        recycleView = findViewById(R.id.recycleViewsuperheroes);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SuperheroAdapter(new ArrayList<>());
        recycleView.setAdapter(adapter);

        loadHeroesToList();

        buttonAdd.setOnClickListener(v -> {
            String name = EditTextName.getText().toString().trim();
            String power = EditTextSuperpower.getText().toString().trim();
            String universe = EditTextUniverse.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Введите имя героя", Toast.LENGTH_LONG).show();
                return;
            }
            Superhero hero = new Superhero();
            hero.name = name;
            hero.superpower = power;
            hero.universe = universe;

            dao.insert(hero);
            Toast.makeText(this, "Герой добавлен", Toast.LENGTH_LONG).show();
            clearFields();
            loadHeroesToList();
        });

        buttonUpdate.setOnClickListener(v -> {
            if (selectedHeroId == -1) {
                Toast.makeText(this, "Выберите героя из списка для обновления", Toast.LENGTH_LONG).show();
                return;
            }

            String name = EditTextName.getText().toString().trim();
            String power = EditTextSuperpower.getText().toString().trim();
            String universe = EditTextUniverse.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Введите имя героя", Toast.LENGTH_LONG).show();
                return;
            }

            Superhero hero = dao.getById(selectedHeroId);
            if (hero == null) {
                Toast.makeText(this, "Герой не найден", Toast.LENGTH_LONG).show();
                return;
            }

            hero.name = name;
            hero.superpower = power;
            hero.universe = universe;

            dao.update(hero);
            Toast.makeText(this, "Герой обновлён", Toast.LENGTH_LONG).show();
            clearFields();
            selectedHeroId = -1;
            loadHeroesToList();
        });

        buttonDelete.setOnClickListener(v -> {
            if (selectedHeroId == -1) {
                Toast.makeText(this, "Выберите героя из списка для удаления", Toast.LENGTH_LONG).show();
                return;
            }

            Superhero hero = dao.getById(selectedHeroId);
            if (hero == null) {
                Toast.makeText(this, "Герой не найден", Toast.LENGTH_LONG).show();
                return;
            }

            dao.delete(hero);
            Toast.makeText(this, "Герой удалён", Toast.LENGTH_LONG).show();
            clearFields();
            selectedHeroId = -1;
            loadHeroesToList();
        });
    }

    private void loadHeroesToList() {
        List<Superhero> heroes = dao.getAll();
        adapter.setHeroes(heroes);
    }

    private void clearFields() {
        EditTextName.setText("");
        EditTextSuperpower.setText("");
        EditTextUniverse.setText("");
    }

    private class SuperheroAdapter extends RecyclerView.Adapter<SuperheroAdapter.HeroViewHolder> {

        private List<Superhero> heroes;
        public SuperheroAdapter(List<Superhero> heroes) {
            this.heroes = heroes;
        }
        public void setHeroes(List<Superhero> heroes) {
            this.heroes = heroes;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new HeroViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
            Superhero hero = heroes.get(position);
            holder.bind(hero);
        }

        @Override
        public int getItemCount() {
            return heroes.size();
        }

        class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private android.widget.TextView textView;
            private Superhero currentHero;
            public HeroViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
                itemView.setOnClickListener(this);
            }
            void bind(Superhero hero) {
                currentHero = hero;
                textView.setText(hero.name + " (" + hero.universe + ")");
            }
            @Override
            public void onClick(View v) {
                selectedHeroId = currentHero.id;
                EditTextName.setText(currentHero.name);
                EditTextSuperpower.setText(currentHero.superpower);
                EditTextUniverse.setText(currentHero.universe);
                Toast.makeText(MainActivity.this, "Герой выбран: " + currentHero.name, Toast.LENGTH_LONG).show();
            }
        }
    }
}