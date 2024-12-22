class CreateUrls < ActiveRecord::Migration[8.0]
  def change
    create_table :urls do |t|
      t.string :title
      t.string :link
      t.text :description

      t.timestamps
    end
  end
end
